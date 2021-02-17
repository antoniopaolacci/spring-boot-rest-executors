

prerequisites:
 - Java jdk8
 - Maven 3.6.3

# Import project on eclipse
mvn eclipse:eclipse

# Build without Test
mvn clean install -DskipTests

# Run project
mvn spring-boot:run

# References:

- Logging resttemplate
https://laptrinhx.com/spring-resttemplate-request-response-logging-1959597283/

- Logging Incoming request
https://www.baeldung.com/spring-http-logging

- GetForObject
https://www.concretepage.com/spring-5/spring-resttemplate-getforobject

- Complete Example
https://spring.io/blog/2009/03/27/rest-in-spring-3-resttemplate

- Spring dependency injection via annotations
https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
