package it.example.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import it.example.app.executors.SoundappService;
import it.example.app.executors.TestService;
import it.example.app.modelbean.planet.Planet;
import it.example.app.modelbean.soundapp.SoundappInfo;

@RestController
public class MyController extends AbstractRequestLoggingFilter {

	Logger logger = LoggerFactory.getLogger(MyController.class);
	
	@Autowired
	TestService myRestService;
	
	@Autowired
	SoundappService soundappService;
	 
	@RequestMapping("/")
	public Planet index() throws Throwable {
		
		logger.info("MyController / invoked.");
		
		Planet planetMBean = new Planet();
		
		planetMBean.setId(123);
		planetMBean.setName("Sole");
		
		Planet modelBean = myRestService.doService(planetMBean);
			
		return modelBean;
		
	}
	
	@RequestMapping("/soundapp")
	public SoundappInfo infoSoundapp() throws Throwable {
		
		logger.info("MyController /soundapp invoked.");
		
		SoundappInfo soundappInfo = new SoundappInfo();
		
		soundappInfo = soundappService.doService(soundappInfo);
			
		return soundappInfo;
		
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.info(message);	
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.info("===========================INPUT request begin================================================");
		logger.debug("URI         : {}", request.getRequestURI());
		logger.debug("Method      : {}", request.getMethod());
		logger.info("==========================INPUT request end================================================");
		logger.info(message);	
	}

}