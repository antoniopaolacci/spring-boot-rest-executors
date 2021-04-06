package it.example.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import it.example.app.executors.services.HelloSoundappService;
import it.example.app.executors.services.PlaySoundappService;
import it.example.app.executors.services.StatsSoundappService;
import it.example.app.executors.services.TestService;
import it.example.app.modelbean.planet.Planet;
import it.example.app.modelbean.soundapp.HelloSoundappInfo;
import it.example.app.modelbean.soundapp.PlaySoundappInfo;
import it.example.app.modelbean.soundapp.StatisticSoundappInfo;
import it.example.app.rest.exceptions.RestServiceCallException;

@RestController
public class MyController extends AbstractRequestLoggingFilter {

	Logger logger = LoggerFactory.getLogger(MyController.class);
	
	@Autowired
	TestService myRestService;
	
	@Autowired
	HelloSoundappService helloSoundappService;
	
	@Autowired
	PlaySoundappService playSoundappService;
	
	@Autowired
	StatsSoundappService statsSoundappService;
	 
	@RequestMapping("/")
	public Planet index() throws RestServiceCallException {
		
		logger.info("MyController / invoked.");
		
		Planet planetMBean = new Planet();
		
		planetMBean.setId(123);
		planetMBean.setName("Sole");
		
		Planet modelBean = myRestService.doService(planetMBean);
			
		return modelBean;
		
	}
	
	@RequestMapping("/info-soundapp")
	public HelloSoundappInfo infoSoundapp() throws RestServiceCallException {
		
		logger.info("MyController /info-soundapp invoked.");
		
		HelloSoundappInfo helloSoundappInfo = new HelloSoundappInfo();
		
		helloSoundappInfo = helloSoundappService.doService(helloSoundappInfo);
			
		return helloSoundappInfo;
		
	}
	
	@RequestMapping("/playing-soundapp")
	public PlaySoundappInfo playSoundapp() throws RestServiceCallException {
		
		logger.info("MyController /playing-soundapp invoked.");
		
		PlaySoundappInfo playSoundappInfo = new PlaySoundappInfo();
		
		playSoundappInfo = playSoundappService.doService(playSoundappInfo);
			
		return playSoundappInfo;
		
	}
	
	@RequestMapping("/statistic-soundapp")
	public StatisticSoundappInfo statsSoundapp() throws RestServiceCallException {
		
		logger.info("MyController /statistic-soundapp invoked.");
		
		StatisticSoundappInfo statisticSoundappInfo = new StatisticSoundappInfo();
		
		statisticSoundappInfo = statsSoundappService.doService(statisticSoundappInfo);
			
		return statisticSoundappInfo;
		
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.info("===========================INBOUND response begin================================================");
		logger.debug("URI         : {}", request.getRequestURI());
		logger.debug("Method      : {}", request.getMethod());
		logger.info("==========================INBOUND response end================================================");
		logger.info(message);		
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.info("===========================INBOUND request begin================================================");
		logger.debug("URI         : {}", request.getRequestURI());
		logger.debug("Method      : {}", request.getMethod());
		logger.info("==========================INBOUND request end================================================");
		logger.info(message);	
	}

}