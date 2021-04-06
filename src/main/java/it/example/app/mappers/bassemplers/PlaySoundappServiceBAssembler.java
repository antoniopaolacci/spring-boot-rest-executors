package it.example.app.mappers.bassemplers;

import it.example.app.mappers.AbstractBusinessAssemblerGenerics;
import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.modelbean.soundapp.PlaySoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.soundapp.playing.MySongBean;
import it.example.app.restbean.soundapp.playing.PlaySoundappRequest;

public class PlaySoundappServiceBAssembler extends AbstractBusinessAssemblerGenerics<PlaySoundappInfo, PlaySoundappInfo, PlaySoundappRequest, MySongBean> implements IBusinessAssemblerGenerics<PlaySoundappInfo, PlaySoundappInfo, PlaySoundappRequest, MySongBean> {

	@Override
	public PlaySoundappRequest doInputMapping(PlaySoundappInfo sourceBean) throws RestServiceCallException {
		return null;
	}

	@Override
	public Object doInputMappingUriMap(PlaySoundappInfo sourceBean) throws RestServiceCallException {
		return null;
	}

	@Override
	public PlaySoundappInfo doOutputMapping(MySongBean response) throws RestServiceCallException {

		PlaySoundappInfo playSoundappInfo = new PlaySoundappInfo();

		playSoundappInfo.setTitle(response.getTitle());
		playSoundappInfo.setUrl(response.getUrl());
		playSoundappInfo.setDuration(response.getDuration());
		
		return playSoundappInfo;

	}

	@Override
	public PlaySoundappInfo doExceptionMapping() {
		return new PlaySoundappInfo();
	}

}