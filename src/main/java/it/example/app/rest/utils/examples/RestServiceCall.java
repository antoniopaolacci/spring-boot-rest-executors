package it.example.app.rest.utils.examples;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.example.app.rest.interceptors.BufferedLoggingInterceptor;
import it.example.app.rest.interceptors.LoggingInterceptor;
import it.example.app.restbean.planet.PlanetRequest;
import it.example.app.restbean.planet.PlanetResponse;
import it.example.app.restbean.soundapp.login.MyLoginBean;
import it.example.app.restbean.soundapp.login.MyLoginBeanResponse;
import it.example.app.restbean.soundapp.playing.MySongBean;

public class RestServiceCall {
	
	private static Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	private RestTemplate restTemplate;
	
	public RestServiceCall(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void init() {
	
		// As logging request and response solution, we can configure Interceptors 
		// for RestTemplate or leveraging Apache HttpClient dependency.
		// But I need to exclude the use of org.springframework.http.client  ( ? )
		this.restTemplate = new RestTemplate();
		this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());;
		
//		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();		
//		interceptors.add(new LoggingInterceptor());	
//		this.restTemplate.setInterceptors(interceptors);
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();		
		interceptors.add(new BufferedLoggingInterceptor());	
		this.restTemplate.setInterceptors(interceptors);
		
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{ MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL }));
	    restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
		
	}
	
	public PlanetResponse doCallGetForEntity() {
	
		log.info("Calling rest endpoint (doCallGetForEntity)...");	
		
		// URL
		String url = "http://localhost:7001/solarSystem/planets/{planet}/moons/{moon}?firstName={firstName}";
		
		// add URI (URL) parameters and query string parameters
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("planet", "Mars");
		urlParams.put("moon", "Phobos");
		urlParams.put("firstName", "Earth");
	
		//Resolved url: http://localhost:7001/solarSystem/planets/Mars/moon/Phobos?firstName=Earth
		ResponseEntity<PlanetResponse> response = this.restTemplate.getForEntity(url, PlanetResponse.class, urlParams);
		
		log.info("Called rest endpoint. PlanetResponse: ["+response+"]");
	
		return response.getBody();
		
	}
	
	public PlanetResponse doCallGetForObject_2() {
		
		log.info("Calling rest endpoint (doCallGetForObject_2)...");	
		
		// Assuming BASE_URL is just a host url like: http://www.somehost.com
		String BASE_URL = "http://www.somehost.com";
		
		Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("id", "5080");
        uriVariables.put("b", "fromOrigin");
		
		URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)    						   	   // Build the base host
										    .path("/android/played/{id}/{b}/c")                        // Add path
										    .queryParam("name", "John Snow")                           // Add one or more query params
										    .buildAndExpand(uriVariables)                              // Build the URL and Expand path variable in URI
										    .encode()                                                  // Encode any URI items that need to be encoded
										    .toUri();                                                  // Convert to URI

		//Resolved url: http://www.somehost.com/android/played/5080/fromOrigin/c?name=John%20Snow
		PlanetResponse response = restTemplate.getForObject(targetUrl, PlanetResponse.class);
		
		log.info("Called rest endpoint. PlanetResponse: ["+response+"]");
		
		return response;
		
	}
	
	public MySongBean doCallGetForObject_3() {

		log.info("Calling rest endpoint (doCallGetForObject_3)...");	

		// call REST API: method GET, body empty, no Path/Query parameters
		String MY_URL = "http://api.soundapp.it/rest/app/song/playing";

		MySongBean response = restTemplate.getForObject(MY_URL, MySongBean.class);

		log.info("Called rest endpoint. MySongBean: ["+response+"]");

		return response;

	}
	
	
	public PlanetResponse doCallExchange() {
		
		log.info("Calling rest endpoint (doCallExchange)...");	
		
		// URL
		String url = "http://localhost:7001/solarSystem/planets/{planet}/moons/{moon}";
		
		// add URI (URL) parameters
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("planet", "Mars");
		urlParams.put("moon", "Phobos");
		
		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		// Add query parameter
		.queryParam("firstName", "Mark")
		.queryParam("lastName", "Watney");
		
		// add Headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");  
		headers.set("X-COM-LOCATION", "USA");
		
		/* Add Request body (JSON)
		 * 
		 * {
		 *	"traceId": "KASJ456789ABC78",
		 *	"clientName": "standalone-client-app"
		 * }
		 * 
		 */ 
		PlanetRequest request = new PlanetRequest();
		request.setClientName("standalone-client-app");
		request.setTraceId("KASJ456789ABC78");
		
		HttpEntity<PlanetRequest> requestEntity = new HttpEntity<>(request, headers);
		
		//Resolved URL: http://localhost:7001/solarSystem/planets/Mars/moons/Phobos?firstName=Mark&lastName=Watney
		ResponseEntity<PlanetResponse> response = this.restTemplate.exchange(builder.buildAndExpand(urlParams).toUri() , HttpMethod.GET, requestEntity, PlanetResponse.class);

		log.info("Called rest endpoint. PlanetResponse: ["+response+"]");
		
		return response.getBody();
		
	}
	
	public PlanetResponse doCallExchange_UsedOnAbstractRestServiceExecutor() {
		
		log.info("Calling rest endpoint (doCallExchange)...");	
		
		// URL
		String url = "http://localhost:7001/solarSystem/planets/{planet}/moons/{moon}?firstName={firstName}&lastName={lastName}";
		
		// add URI (URL) parameters
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("planet", "Mars");
		uriVariables.put("moon", "Phobos");
		uriVariables.put("firstName", "Mark");
		uriVariables.put("lastName", "Watney");
		
		
		/* Add Request body (JSON)
		 * 
		 * {
		 *	"traceId": "KASJ456789ABC78",
		 *	"clientName": "standalone-client-app"
		 * }
		 * 
		 */ 
		PlanetRequest request = new PlanetRequest();
		request.setClientName("standalone-client-app");
		request.setTraceId("KASJ456789ABC78");
		
		HttpEntity<PlanetRequest> requestEntity = new HttpEntity<>(request);
		
		//Resolved URL: http://localhost:7001/solarSystem/planets/Mars/moons/Phobos?firstName=Mark&lastName=Watney
		ResponseEntity<PlanetResponse> response = this.restTemplate.exchange(url, HttpMethod.GET, requestEntity, PlanetResponse.class, uriVariables);

		log.info("Called rest endpoint. PlanetResponse: ["+response+"]");
		
		return response.getBody();
		
	}
	
	public void doCallPostForEntity() {

		log.info("Calling rest endpoint (doCallPostForEntity)...");	

		// call REST API: method GET, body empty, no Path/Query parameters
		String MY_URL = "http://api.soundapp.it/rest/auth/login";

		// create HTTP Entity request
		
		MyLoginBean myLoginBean = new MyLoginBean();
		myLoginBean.setUsername("string");
		myLoginBean.setPassword("string");
		
		// create JSON headers
		HttpHeaders  headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<MyLoginBean> request = new HttpEntity<MyLoginBean>(myLoginBean, headers);
		
		ResponseEntity<MyLoginBeanResponse> responseEntity = restTemplate.postForEntity(MY_URL, request, MyLoginBeanResponse.class);
		
		log.info("MyLoginBeanResponse: "+responseEntity.getBody());
		
		MyLoginBeanResponse response = responseEntity.getBody();
			
		log.info("ID: ["+response.getUniqueID()+"] USER: ["+response.getFirstName()+" "+response.getLastName()+"]");
				
	}
	
	public void doCallStatsSoundapp() throws JsonMappingException, JsonProcessingException {

		log.info("Calling rest endpoint (doCallStatsSoundapp)...");	

		// call REST API: method GET, body empty, no Path/Query parameters
		String MY_URL = "http://www.soundapp.it/stats";

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(MY_URL, String.class);
		
		log.info("Response: "+responseEntity.getBody());
		/*
		 * { 
		 *   "count":"0",  
		 *   "now":"",
         *   "next":"", 
         *   "previous": "Max Gazze - Il farmacista", 
         *     "history": [
	     *            {"title":"M?neskin - ZITTI E BUONI (Official Video - Sanremo 2021)"},
	     *            {"title":"Max Gazz? - Il farmacista"},{"title":"Daniele Silvestri - Il mio nemico (videoclip)"},
	     *            {"title":"Queen - Radio Ga Ga (Official Video)"},{"title":"Thomas Cheval - Acqua Minerale // Official Music Video"},
	     *            {"title":"Brunori Sas - Secondo me"},{"title":"Irama - La genesi del tuo colore (Official Video) [Sanremo 2021]"},
	     *            {"title":"MIKA - Popular Song ft. Ariana Grande"},{"title":"Willie Peyote - Mai Dire Mai (La Locura)"},
	     *            {"title":"Mika - Elle Me Dit (clip officiel)"},{"title":"Baustelle - La guerra ? finita (Official Video)"},
	     *            {"title":"MEGANOIDI  -  Zeta Reticoli  - Video Clip Ufficiale"},{"title":"Levante - Tikibombom (Home Version)"},
	     *            {"title":"La Rappresentante di Lista - Amare (Official Video - Sanremo 2021)"}
         *       ]}
		 *
		 */
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode actualObj = objectMapper.readTree(responseEntity.getBody());
		
		JsonNode jsonNode = actualObj.get("previous");
		String title = jsonNode.textValue();

		JsonNode arrNode =  actualObj.get("history");
		if (arrNode.isArray()) {
			for (final JsonNode objNode : arrNode) {
				log.info(objNode.get("title").textValue());
			}
		}

		log.info("PREV: "+title);
	    
	}
	
	
	
	public static void main(String[] args) {
		
		ApplicationContext context =  new ClassPathXmlApplicationContext("webconfig-beans.xml");

	    RestTemplate restTemplate = (RestTemplate) context.getBean("HttpTestRestTemplate");  // or BufferingRestTemplate or HttpTestRestTemplate
		
		RestServiceCall restServiceCall = new RestServiceCall(restTemplate);  // or new RestTemplate()

//		restServiceCall.init();  // or you can initialize a RestServiceCall with different RestTemplate programmatically 
		
		log.info("-------------- EXAMPLES doCallExchange --------------");
		try {
			restServiceCall.doCallExchange();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallGetForEntity --------------");
		try {
			restServiceCall.doCallGetForEntity();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallGetForObject_2 --------------");
		try {
			restServiceCall.doCallGetForObject_2();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallGetForObject_3 --------------");
		try {
			restServiceCall.doCallGetForObject_3();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallExchange_UsedOnAbstractRestServiceExecutor --------------");
		try {
			restServiceCall.doCallExchange_UsedOnAbstractRestServiceExecutor();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallPostForEntity --------------");
		try {
			restServiceCall.doCallPostForEntity();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
		log.info("-------------- EXAMPLES doCallStatsSoundapp --------------");
		try {
			restServiceCall.doCallStatsSoundapp();
		} catch(Exception e) {
			log.error("Error: ",e);
		}
		
	}
	
}

