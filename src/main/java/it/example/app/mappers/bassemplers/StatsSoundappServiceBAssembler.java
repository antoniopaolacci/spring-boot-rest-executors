package it.example.app.mappers.bassemplers;

import it.example.app.mappers.AbstractBusinessAssemblerGenerics;
import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.modelbean.soundapp.StatsSoundappInfo;
import it.example.app.restbean.soundapp.StatsSoundappRequest;
import it.example.app.restbean.soundapp.StatsSoundappResponse;

public class StatsSoundappServiceBAssembler extends AbstractBusinessAssemblerGenerics<StatsSoundappInfo, StatsSoundappInfo, StatsSoundappRequest, StatsSoundappResponse> implements IBusinessAssemblerGenerics<StatsSoundappInfo, StatsSoundappInfo, StatsSoundappRequest, StatsSoundappResponse> {

	@Override
	public StatsSoundappRequest doInputMapping(StatsSoundappInfo sourceBean) throws Throwable {
		return null;
	}

	@Override
	public Object doInputMappingUriMap(StatsSoundappInfo sourceBean) throws Throwable {
		return null;
	}

	@Override
	public StatsSoundappInfo doOutputMapping(StatsSoundappResponse response) throws Throwable {

		StatsSoundappInfo statsSoundappInfo = new StatsSoundappInfo();

		statsSoundappInfo.setNumSongEnqueued(response.getCount());
		statsSoundappInfo.setNextSong(response.getNext());
		statsSoundappInfo.setPreviousSong(response.getPrevious());
		statsSoundappInfo.setHistoryPlayedSongs(response.getHistory());
		
		return statsSoundappInfo;

	}

}