package it.example.app.mappers.bassemplers;

import it.example.app.mappers.AbstractBusinessAssemblerGenerics;
import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.modelbean.planet.Planet;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.planet.PlanetRequest;
import it.example.app.restbean.planet.PlanetResponse;
import it.example.app.restbean.planet.PlanetUri;

public class TestServiceBAssembler extends AbstractBusinessAssemblerGenerics<Planet, Planet, PlanetRequest, PlanetResponse> implements IBusinessAssemblerGenerics<Planet, Planet, PlanetRequest, PlanetResponse> {

	@Override
	public PlanetRequest doInputMapping(Planet sourceBean) throws RestServiceCallException {
		
		PlanetRequest request = new PlanetRequest();
		request.setTraceId(String.valueOf(sourceBean.getId()));
		request.setClientName("TEST");
		return request;
		
	}

	@Override
	public Object doInputMappingUriMap(Planet sourceBean) throws RestServiceCallException {

		// Path params
		PlanetUri planetUri = new PlanetUri();
		planetUri.setPlanet(sourceBean.getName());
		planetUri.setMoon("fakeMoonValue");
		
		// Query params
		planetUri.setFirstName("value1");
		planetUri.setLastName("value2");
		
		return planetUri;
		
	}

	@Override
	public Planet doOutputMapping(PlanetResponse response) throws RestServiceCallException {
		
		Planet modelBean = new Planet();
		modelBean.setId(Integer.parseInt(response.getSolarDistance()));
		modelBean.setName(response.getName());
		return modelBean;
		
	}

	@Override
	public Planet doExceptionMapping() {
		return new Planet();
	}

}
