package it.example;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@ImportResource("classpath:webconfig-beans.xml")
@SpringBootApplication
public class Application {

	Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("--- Let's inspect the beans provided by Spring Boot: ---\n");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			
			StringBuilder sb = new StringBuilder();
			for (String beanName : beanNames) {
				sb.append(beanName+" ");
			}
			
			logger.info(sb.toString());

		};
	}
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setMaxPayloadLength(64000);
	    
	    return loggingFilter;
	}
	
}