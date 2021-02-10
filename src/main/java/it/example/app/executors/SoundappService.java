package it.example.app.executors;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.example.app.modelbean.soundapp.SoundappInfo;
import it.example.app.restbean.soundapp.SoundappRequest;
import it.example.app.restbean.soundapp.SoundappResponse;

public class SoundappService extends AbstractRestServiceExecutor<SoundappInfo, SoundappInfo, SoundappRequest, SoundappResponse> {

	@Override
	protected SoundappResponse callOperation(HttpEntity<?> requestEntity, Map<String, String> uriParams) throws Throwable {
		return this.restTemplate.getForObject(this.baseUrl, SoundappResponse.class);
	}

	@Override
	protected void addExternalHeaders(HttpHeaders requestHeaders) {
		// TODO Auto-generated method stub
	}

}
