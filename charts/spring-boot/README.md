<!-- omit in toc -->
# Spring Boot Helm Chat
A simple, yet flexible helm chart to deploy spring boot applications in kubernetes.

Let's put an end to low effort helm charts for our spring boot applications that all do the same,
yet are copy-pasted a hundred times for a hundred services.

## Promises
**One chart to rule them all:**\
Set your image repository, set your application configuration, install, enjoy.
**Simple:** Your chart is basically helm create. So are we.\
**Flexible:** All your concerns should be met. You think your microservice deployment has a need that's not satisfied? [Raise an issue](../../CONTRIBUTING.md).\
**Tidy**: Not only do we free you from the burden of maintaining your spring boot helm charts, your configuration will be even cleaner. See our recommendations below.

# Getting started
In prerelease, you can consume it with helm-git. Add the repo
```shell
helm repo add goatfryed git+https://github.com/goatfryed/helm-charts@charts?ref=main
```

Create your application-yourEnv.yaml
```yaml
exampleApp:
  rest:
    buddyApp:
      baseUrl: http://buddyApp/api
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s
logging:
  level:
    org.springframework: error
    com.example.myapp: debug
server:
  compression:
    enabled: true
    mime-types: application/json,text/html
    min-response-size: 1024   
```

Install your app:
```shell
helm install \
  spring-boot-app goatfryed/spring-boot \
  --set image.repository=yourRegistry/yourRepo \
  --set-file spring.config.local.values=application-yourEnv.yaml
```

# Documentation
We are terrible sorry to not provide a better documentation at the moment. It's coming soon!

For now, please see the documentation in the [default values](values.yaml) or take a look at the
[unit tests](./tests)

# Contributing
Looking to contribute? Great! Check out [CONTRIBUTING.md](../../CONTRIBUTING.md)
