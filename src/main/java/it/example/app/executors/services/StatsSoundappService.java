package it.example.app.executors.services;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.example.app.executors.AbstractRestServiceExecutor;
import it.example.app.modelbean.soundapp.StatisticSoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.soundapp.stats.StatsSoundappRequest;

public class StatsSoundappService extends AbstractRestServiceExecutor<StatisticSoundappInfo, StatisticSoundappInfo, StatsSoundappRequest, String> {

	@Override
	protected String callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws RestServiceCallException {
		return this.restTemplate.getForEntity(this.baseUrl, String.class).getBody();
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		// nothing to do
	}

}