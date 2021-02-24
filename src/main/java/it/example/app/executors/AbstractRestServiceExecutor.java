package it.example.app.executors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.rest.interceptors.LoggingInterceptor;

/**
 *
 * Classe per le chiamate Restfull...
 *
 * @param <T1> ModelBean di input del BAssembler
 * @param <T2> ModelBean di output del BAssembler
 * @param <T3> Service Request del BAssembler
 * @param <T4> Service Response del BAssembler
 * 
 */

public abstract class AbstractRestServiceExecutor<T1, T2, T3, T4> {

	private static Logger log = LoggerFactory.getLogger(AbstractRestServiceExecutor.class);

	private IBusinessAssemblerGenerics<T1, T2, T3, T4> businessAssembler; 	// BusinessAssembler

	public String baseUrl; 		// BASE_URL

	public String xApiKey; 		// Example of API KEY

	@Autowired
	@Qualifier("HttpTestRestTemplate")  // BufferingRestTemplate or HttpTestRestTemplate
	protected RestTemplate restTemplate;
	
	// Indica se abilitato il log & tracing verboso
	private Boolean isVerbose;

//  Or you can leveraging @PostConstruct to inizialize with different rest template	
	
//	@PostConstruct 
//	private void init() {
//			
//		    log.info("Initializing AbstractRestServiceExecutor...");
//		
//			// As logging request and response solution, we can configure interceptors for RestTemplate.
//		    // For instance, if we want our interceptor to function as a request/response logger, 
//		    // then we need to read it twice – the first time by the interceptor and the second time by the client.
//		    // The default implementation allows us to read the response stream only once. To cater such specific scenarios, 
//		    // Spring provides a special class called BufferingClientHttpRequestFactory. 
//		    // As the name suggests, this class will buffer the request/response in JVM memory for multiple usage.
//			this.restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(
//												 new SimpleClientHttpRequestFactory())
//												 );
//			
//			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();		
//			interceptors.add(new LoggingInterceptor());	
//			this.restTemplate.setInterceptors(interceptors);
//			
//			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
//			
//			//Add the Jackson Message converter
//			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//			// Note: here we are making this converter to process any kind of response, 
//			// not only application/*json, which is the default behaviour 
//			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
//			messageConverters.add(converter);  
//			this.restTemplate.setMessageConverters(messageConverters); 
//			
//			log.info("Initialized AbstractRestServiceExecutor with interceptors and message converters."); 
//			
//	}
	
	// Metodo richiamato dal controller per eseguire la chiamata al servizio 

	public T2 doService(T1 t1) throws RestServiceCallException {

		log.info("Inside doService "+t1.toString());
		log.info("businessAssembler "+businessAssembler.toString());
		
		long timeStart = Calendar.getInstance().getTimeInMillis();

		T2 t2;
		
		try {
		
				// ModelBean Input to Service Request
				T3 t3 = this.businessAssembler.doInputMapping(t1);
		
				Map<String, String> uriParams = beanToMap(this.businessAssembler.doInputMappingUriMap(t1));
		
				// Create HttpHeaders
				HttpHeaders requestHeaders = createHttpHeader();
				
				// Create HttpEntity request
				HttpEntity<?> request = new HttpEntity<Object>(t3, requestHeaders);;
				
				timeStart = Calendar.getInstance().getTimeInMillis();
		
				log.info(this.baseUrl + " ClassName=" +this.getClass().getCanonicalName() + " INIT Rest Service Call");
		
				// Call to service
				T4 t4 = this.callOperation(request, uriParams);
		
				long timeEnd = Calendar.getInstance().getTimeInMillis();
		
				log.info(this.baseUrl, " ClassName=" + this.getClass().getCanonicalName(), " END Rest Service Call (elapsed time = " + Long.toString(timeEnd - timeStart) + ")");
		
				// Service Request to ModelBean Output
				t2 = this.businessAssembler.doOutputMapping(t4);

		} catch (Throwable e) {
			
			throw new RestServiceCallException(e);
		}
		
		return t2;
		
	}

	public String getxApiKey() {
		return xApiKey;
	}

	public void setxApiKey(String xApiKey) {
		this.xApiKey = xApiKey;
	}

	public Boolean getIsVerbose() {
		return isVerbose;
	}

	public void setIsVerbose(Boolean isVerbose) {
		this.isVerbose = isVerbose;
	}

	protected abstract T4 callOperation(HttpEntity<?>  requestEntity, Map<String, String> uriParams) throws Throwable;


	public IBusinessAssemblerGenerics<T1, T2, T3, T4> getBusinessAssembler() {
		return this.businessAssembler;
	}

	public void setBusinessAssembler(IBusinessAssemblerGenerics<T1, T2, T3, T4> businessAssembler) {
		this.businessAssembler = businessAssembler;
	}

	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


	private HttpHeaders createHttpHeader() {

		// Set header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));

		// Set external header
		addExternalHeaders(requestHeaders);

		// The body object as first parameter
		return requestHeaders;

	}
	
	protected abstract void addExternalHeaders(HttpHeaders requestHeaders);

	protected Map<String,String> beanToMap(Object ob) {

		Map<String,String> parameters  = null;

		if (ob != null) {
			TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
			ObjectMapper mapper = new ObjectMapper();
			parameters = mapper.convertValue(ob, typeRef);
		}

		return parameters;
	}

	public String getEndpointUriMap(Map<String,String> uriMap) {

		String endPoint = this.baseUrl+"?";

		for (Map.Entry<String, String> param : uriMap.entrySet()) {
			if(param.getValue()!=null){
				if(!endPoint.contains("{"+param.getKey()+"}"))
					endPoint = endPoint.concat(param.getKey()+"={"+param.getKey()+"}"+"&");		
			}				
		}

		return endPoint.endsWith("&") ? StringUtils.substring(endPoint, 0, endPoint.length()-1) : endPoint;	 

	}
	
	

}