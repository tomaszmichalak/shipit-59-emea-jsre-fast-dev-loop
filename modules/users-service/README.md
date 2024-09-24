# Users Service
External service that provides user information. It is a wiremock service that returns user information for a given email.

## Build
From `modules/users-service/code` run:

```bash
docker build -t users-service .
```

## Run as container
```bash
docker run -p 8081:8080 users-service
```

and open [localhost:8081/user/existingUser@atlassian.com](http://localhost:8081/user/existingUser@atlassian.com) in your browser.