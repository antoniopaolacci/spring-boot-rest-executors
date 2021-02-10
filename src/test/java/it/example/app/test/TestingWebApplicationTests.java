package it.example.app;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.util.Assert;

import it.example.app.modelbean.planet.Planet;

@SpringBootTest
public class TestingWebApplicationTests {

	@Test
	public void testNumberOne() {
	    
		TestRestTemplate testRestTemplate = new TestRestTemplate();
	    
		Planet planet = new Planet("Saturno", 154789);
	    
		Planet output = testRestTemplate.getForObject("http://localhost:7001/", Planet.class);
	 
		Assert.isInstanceOf(Planet.class, output);
		
	}

}