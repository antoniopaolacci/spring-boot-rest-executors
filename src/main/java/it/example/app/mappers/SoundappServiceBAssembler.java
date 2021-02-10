package it.example.app.mappers;

import it.example.app.modelbean.soundapp.SoundappInfo;
import it.example.app.restbean.soundapp.SoundappRequest;
import it.example.app.restbean.soundapp.SoundappResponse;

public class SoundappServiceBAssembler extends AbstractBusinessAssemblerGenerics<SoundappInfo, SoundappInfo, SoundappRequest, SoundappResponse> implements IBusinessAssemblerGenerics<SoundappInfo, SoundappInfo, SoundappRequest, SoundappResponse> {

	@Override
	public SoundappRequest doInputMapping(SoundappInfo sourceBean) throws Throwable {
		return null;
	}

	@Override
	public Object doInputMappingUriMap(SoundappInfo sourceBean) throws Throwable {
		return null;
	}

	@Override
	public SoundappInfo doOutputMapping(SoundappResponse response) throws Throwable {

		SoundappInfo soundappInfo = new SoundappInfo();

		soundappInfo.setDescription(response.getDescription());
		soundappInfo.setSummary(response.getSummary());

		return soundappInfo;

	}

}
