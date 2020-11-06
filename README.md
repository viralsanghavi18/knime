
## Nevernote app

### Run the app
From Knime-Nevernote directory run
```bash
docker-compose up -d
```

## Swagger
http://localhost:8080/nevernote/swagger-ui/index.html

## Metrics
http://localhost:8080/nevernote/actuator/prometheus

## Postman Collection
Added Nevernote.postman_collection.json file for a quick start for you to test out

## Design Overview
1. *Spring Boot* - It is simple and quick to spin up a REST based microservice. The annotation based features, stable stateful connectors (DB, Kafka, Caches) and detailed monitoring support are very helpful.  
2. *Stateful* - Avoided DB considering time and to reduce complexity of the solution. Stateful feature is achieved by Redis cache. Note contents are stored as a file on the file system.
3. *Performance* - Other than reading and writing note & notebooks, performance would be critical in filter by tags actions. Used Redis cache to store mapping of tag & respective files containing tags.

## Production ready improvements

1. Large file handling
2. failure handling and retries
3. Logging, exception handling and business validations
4. Additional unit tests
5. Robust monitoring
6. Externalization of the property file
7. Interceptors for common actions
8. Scalability testing
9. Rule engine integration
10. Performance benchmarking
11. Test automation
12. DevOps pipelines
13. Container orchestrator
14. Data security (DB, file system, cache)
15. Authentication, authorization
 