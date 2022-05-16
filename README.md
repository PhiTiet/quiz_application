# Quiz Application

## How to run:
### Run from (fat) jar:
```mvn clean install```

``` java -jar .\target\quiz-1.jar```

### Run using Maven & Spring
```mvn spring-boot:run```

### Run as a Docker container 

```docker build --tag=quiz_application . ```

```docker run -p8080:8069 -d quiz_application```

## Assumptions:

-Question provided by api is unique per question string.

-Multiple Users may access the endpoint (Hashmap for small mem-stack)

-Api never provides malicious data (```th:utext``` unsafe for unsantized text)

## Design choices:

- My main goal was to show off a well-structured project with snippets of java, thymeleaf, bootstrap and javascript.

- No error-handling & unit testing as it is out of the scope of this project.

- Develop branch/main branch flow to illustrate an example usage of gitflow-like flow.

- No Service layer for the sake of simplicity

- Deserialization through ```@Responsebody``` for the sake of simplicity.

- Simple, functional, minimalistic frontend also for the sake of simplicity.

- Fetching 5 question at a time instead of a large batch to keep the spirit of speaking to the opentdb api through a client interface.