package it.example.app.executors.services;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.example.app.executors.AbstractRestServiceExecutor;
import it.example.app.modelbean.soundapp.StatsSoundappInfo;
import it.example.app.restbean.soundapp.StatsSoundappRequest;
import it.example.app.restbean.soundapp.StatsSoundappResponse;

public class StatsSoundappService extends AbstractRestServiceExecutor<StatsSoundappInfo, StatsSoundappInfo, StatsSoundappRequest, StatsSoundappResponse> {

	@Override
	protected StatsSoundappResponse callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws Throwable {
		return this.restTemplate.getForObject(this.baseUrl, StatsSoundappResponse.class);
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		// TODO Auto-generated method stub
	}

}