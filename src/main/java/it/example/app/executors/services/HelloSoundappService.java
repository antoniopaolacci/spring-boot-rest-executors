package it.example.app.executors.services;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.example.app.executors.AbstractRestServiceExecutor;
import it.example.app.modelbean.soundapp.HelloSoundappInfo;
import it.example.app.restbean.soundapp.HelloSoundappRequest;
import it.example.app.restbean.soundapp.HelloSoundappResponse;

public class HelloSoundappService extends AbstractRestServiceExecutor<HelloSoundappInfo, HelloSoundappInfo, HelloSoundappRequest, HelloSoundappResponse> {

	@Override
	protected HelloSoundappResponse callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws Throwable {
		return this.restTemplate.getForObject(this.baseUrl, HelloSoundappResponse.class);
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		// TODO Auto-generated method stub
	}

}
