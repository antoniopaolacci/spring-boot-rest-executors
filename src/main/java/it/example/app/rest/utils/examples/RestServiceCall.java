package it.example.app.rest.utils.examples;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.example.app.rest.interceptors.BufferedLoggingInterceptor;
import it.example.app.rest.interceptors.LoggingInterceptor;
import it.example.app.restbean.planet.PlanetRequest;
import it.example.app.restbean.planet.PlanetResponse;

public class RestServiceCall {
	
	private static Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	private RestTemplate restTemplate;
	
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
		
		URI targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)    						   // Build the base link
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
	
	public static void main(String[] args) {
		
		RestServiceCall restServiceCall = new RestServiceCall();
		
		restServiceCall.init();
		
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
		
	}
	
}

