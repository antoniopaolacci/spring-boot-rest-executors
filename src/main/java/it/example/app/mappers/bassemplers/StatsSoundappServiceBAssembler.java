package it.example.app.mappers.bassemplers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.example.app.mappers.AbstractBusinessAssemblerGenerics;
import it.example.app.mappers.IBusinessAssemblerGenerics;
import it.example.app.modelbean.soundapp.StatisticSoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;
import it.example.app.restbean.soundapp.stats.StatsSoundappRequest;


public class StatsSoundappServiceBAssembler extends AbstractBusinessAssemblerGenerics<StatisticSoundappInfo, StatisticSoundappInfo, StatsSoundappRequest, String> implements IBusinessAssemblerGenerics<StatisticSoundappInfo, StatisticSoundappInfo, StatsSoundappRequest, String> {

	@Override
	public StatsSoundappRequest doInputMapping(StatisticSoundappInfo sourceBean) throws RestServiceCallException {
		
		StatsSoundappRequest request = new StatsSoundappRequest();
		return request;
		
	}

	@Override
	public Object doInputMappingUriMap(StatisticSoundappInfo sourceBean) throws RestServiceCallException {

		// Path params
		// nothing to do
		
		// Query params
		// nothing to do
		
		return null;
		
	}

	@Override
	public StatisticSoundappInfo doOutputMapping(String response) throws RestServiceCallException {
		
		StatisticSoundappInfo statisticSoundappInfo = new StatisticSoundappInfo();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode actualObj;
	
		try {

			actualObj = objectMapper.readTree(response);
			
			JsonNode jsonNode = actualObj.get("previous");
			String previousTitle = jsonNode.textValue();
			statisticSoundappInfo.setPrev(previousTitle);
			
			JsonNode jsonNode_2 = actualObj.get("now");
			String nowTitle = jsonNode_2.textValue();
			statisticSoundappInfo.setNow(nowTitle);
			
			JsonNode jsonNode_3 = actualObj.get("next");
			String nextTitle = jsonNode_3.textValue();
			statisticSoundappInfo.setNext(nextTitle);
			
			JsonNode arrNode =  actualObj.get("history");
			if (arrNode.isArray()) {
				for (JsonNode objNode : arrNode) {
					statisticSoundappInfo.addHistoryItem(objNode.get("title").textValue());
				}
			}
		
		} catch (JsonProcessingException e) {
			throw new RestServiceCallException(e);
		}
		
		
		return statisticSoundappInfo;

	}

	@Override
	public StatisticSoundappInfo doExceptionMapping() {
		return new StatisticSoundappInfo();
	}

}
