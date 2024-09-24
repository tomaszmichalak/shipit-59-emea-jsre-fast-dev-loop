# Recommendations Service
External service that provides recommendations for a user. It is a wiremock service that returns a list of recommendations for a given user id.

## Build
From `modules/recommendations-service/code` run:

```bash
docker build -t recommendations-service .
```

## Run as container
```bash
docker run -p 8082:8080 recommendations-service
```

and open [localhost:8082/recommendations/user1](http://localhost:8082/recommendations/user1) in your browser.