package it.example.app.executors.services;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import it.example.app.executors.AbstractRestServiceExecutor;
import it.example.app.modelbean.soundapp.PlaySoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.soundapp.playing.MySongBean;
import it.example.app.restbean.soundapp.playing.PlaySoundappRequest;

public class PlaySoundappService extends AbstractRestServiceExecutor<PlaySoundappInfo, PlaySoundappInfo, PlaySoundappRequest, MySongBean> {

	@Override
	protected MySongBean callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws RestServiceCallException {
		return this.restTemplate.getForObject(this.baseUrl, MySongBean.class);
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		// TODO Auto-generated method stub
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","octet-stream")));
	}

}