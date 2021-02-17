package it.example.app.rest.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.example.app.modelbean.planet.Planet;

public class RestUtils {

	public static Map<String,String> beanToMap(Object ob) {

		Map<String,String> parameters  = null;

		if (ob != null) {
			TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
			ObjectMapper mapper = new ObjectMapper();
			parameters = mapper.convertValue(ob, typeRef);
		}

		return parameters;
	}

	
	public static String getEndpointUriMap(Map<String, String> uriMap) {

		String endPoint = SERVICE_URL+"?";

		for (Map.Entry<String, String> param : uriMap.entrySet()) {
			if(param.getValue()!=null){
				if(!endPoint.contains("{"+param.getKey()+"}"))
					endPoint = endPoint.concat(param.getKey()+"={"+param.getKey()+"}"+"&");		
			}				
		}

		return endPoint.endsWith("&") ? StringUtils.substring(endPoint, 0, endPoint.length()-1) : endPoint;	 

	}
	
	private static String SERVICE_URL = "http://test.com/{a}/{name}/c";
	
	public static void main(String[] args) {
		
		Planet planet = new Planet();
		
		planet.setName("Earth");
		planet.setId(123456);
		
		Map<String,String> mapBean = beanToMap(planet);
		
		System.out.println("A ModelBean to Map with some parameters yet on instance variables (name): ["+mapBean+"]");
		
		String expandedServiceUrl = getEndpointUriMap(mapBean);
		
		System.out.println("serviceUrl: ["+SERVICE_URL+"] expandedServiceUrl: ["+expandedServiceUrl+"]. (name) not added !");
		
	}

}
