package it.example.app.mappers.bassemplers;

import it.example.app.mappers.AbstractBusinessAssemblerGenerics;
import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.modelbean.soundapp.HelloSoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.soundapp.info.HelloSoundappRequest;
import it.example.app.restbean.soundapp.info.HelloSoundappResponse;

public class HelloSoundappServiceBAssembler extends AbstractBusinessAssemblerGenerics<HelloSoundappInfo, HelloSoundappInfo, HelloSoundappRequest, HelloSoundappResponse> implements IBusinessAssemblerGenerics<HelloSoundappInfo, HelloSoundappInfo, HelloSoundappRequest, HelloSoundappResponse> {

	@Override
	public HelloSoundappRequest doInputMapping(HelloSoundappInfo sourceBean) throws RestServiceCallException {
		return null;
	}

	@Override
	public Object doInputMappingUriMap(HelloSoundappInfo sourceBean) throws RestServiceCallException {
		return null;
	}

	@Override
	public HelloSoundappInfo doOutputMapping(HelloSoundappResponse response) throws RestServiceCallException {

		HelloSoundappInfo helloSoundappInfo = new HelloSoundappInfo();

		helloSoundappInfo.setDescription(response.getDescription());
		helloSoundappInfo.setSummary(response.getSummary());

		return helloSoundappInfo;

	}

	@Override
	public HelloSoundappInfo doExceptionMapping() {
		return new HelloSoundappInfo();
	}

}
