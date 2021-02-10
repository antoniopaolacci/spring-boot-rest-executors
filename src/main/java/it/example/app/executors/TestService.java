package it.example.app.executors;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import it.example.app.modelbean.planet.Planet;
import it.example.app.restbean.planet.PlanetRequest;
import it.example.app.restbean.planet.PlanetResponse;

public class TestService extends AbstractRestServiceExecutor<Planet, Planet, PlanetRequest, PlanetResponse> {

	@Override
	protected PlanetResponse callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws Throwable {
		return this.restTemplate.exchange(getEndpointUriMap(uriParams), HttpMethod.GET, requestEntity, PlanetResponse.class, uriParams).getBody();
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		requestHeaders.add("X-API-KEY", this.xApiKey);
	}

}
