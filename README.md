
## Prerequisites:
 - Java 8
 - Maven 3.6.3

## Import project on eclipse
mvn eclipse:eclipse

## Build without Test
mvn clean install -DskipTests

## Run project
mvn spring-boot:run

## Test:

| Endpoint REST | Backend call |
| --- | --- |
| http://localhost:8091/ | GET http://localhost:7001/solarSystem/planets/Sole/moons/fakeMoonValue |
| http://localhost:8091/info-soundapp | GET http://api.soundapp.it/rest/hello/json |
| http://localhost:8091/playing-soundapp | GET http://api.soundapp.it/rest/app/song/playing |
| http://localhost:8091/stats-soundapp | GET http://www.soundapp.it/stats |

### Swagger provides a utility jar that allows us to generate REST clients for various programming languages and multiple frameworks.

#### Windows:

java -jar swagger-codegen-cli-2.4.18.jar generate ^
  -DhideGenerationTimestamp=true ^
  -i http://api.soundapp.it/rest/swagger.json ^
  --api-package it.example.soundapp.client.api ^
  --model-package it.example.soundapp.client.model ^
  --invoker-package it.example.soundapp.client.invoker ^
  --group-id it.example ^
  --artifact-id spring-swagger-codegen-api-client ^
  --artifact-version 0.0.1-SNAPSHOT ^
  -l java ^
  --library resttemplate ^
  -o spring-swagger-codegen-api-client
  
#### Unix:

java -jar swagger-codegen-cli-2.4.18.jar generate /
  -DhideGenerationTimestamp=true /
  -i http://api.soundapp.it/rest/swagger.json /
  --api-package it.example.soundapp.client.api /
  --model-package it.example.soundapp.client.model /
  --invoker-package it.example.soundapp.client.invoker /
  --group-id it.example /
  --artifact-id spring-swagger-codegen-api-client /
  --artifact-version 0.0.1-SNAPSHOT /
  -l java /
  --library resttemplate /
  -o spring-swagger-codegen-api-client
