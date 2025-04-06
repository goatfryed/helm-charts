<!-- omit in toc -->
# Spring Boot Helm Chat

⚠ MOVED TO [goatfryed/easy-spring-boot](https://github.com/goatfryed/helm-charts/) ⚠

A simple, yet flexible helm chart to deploy spring boot applications in kubernetes.

Because most helm charts for spring applications are just helm chart init,
but it can be simpler, yet more convenient.\
Also because other available helm charts for spring services are poorly document, not convenient or just helm init.

- [Getting started](#getting-started)
- [Features](#features)
    - [Easy profile configuration management](#easy-profile-configuration-management)
    - [Deployment debug mode](#debug-convenience-to-get-started)

## Promises
**One chart to rule them all:**\
Set your image repository, set your application configuration, install, enjoy.
**Simple:** Your chart is basically helm create. So are we.\
**Flexible:** All your concerns should be met. You think your microservice deployment has a need that's not satisfied? [Raise an issue](../../CONTRIBUTING.md).\
**Tidy**: Not only do we free you from the burden of maintaining your spring boot helm charts, your configuration will be even cleaner. See our recommendations below.

# Getting started
```shell
helm repo add goatfryed https://goatfryed.github.io/helm-charts
```

Create an application.yaml with your environment specific values:
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
  my-spring-app goatfryed/easy-spring-boot \
  --set image.repository=myRegistry/myImageRepo \
  --set-file spring.config.local.values=application.yaml
```

# Features
All configuration options are documented in [this chart's values.yaml](values.yaml). The following documentation only
highlights selected features by example and explains the purpose. For detailed configuration option, see the values.yaml.

Also check out the [unit tests](./tests) for usage examples.

We are terrible sorry to not provide a better documentation at the moment!

## Managed spring configuration
The following spring configuration is manged by the helm chart.

| Spring boot                      | Chart                                                        |
|----------------------------------|--------------------------------------------------------------|
| management.server.port           | spring.managementPort                                        |
| server.port                      | spring.serverPort                                            |
| spring.application.name          | spring.applicationName                                       |
| spring.profiles.active           | [profile management](#easy-profile-configuration-management) |
| spring.config.additionalLocation | *internal*                                                   |


## Easy profile configuration management
You can configure profile specific application properties under `spring.config.{myProfileName}` from different sources.
By default, all configured profiles are activated ordered by name.

You can also use  list active profiles explicitly under `spring.profiles.active` to disable some,
add profiles built into your application or change the priority.
Keep in mind that the last listed profile wins on overlapping definitions.

### define application values directly in your helm value
The quick and easy way to get started with a service configuration. Define environment configuration directly in your
values.yaml
```yaml
spring:
  config:
    myProfile:
      app:
        foo: bar
      logging:
        level:
          root: WARN
```
### define application.yaml in your devops project and inject it
This is the recommended approach for non-critical configuration properties.
Store one or more simple application.yaml for your spring application and inject them as release values.

Use the helm option `--set-file spring.config.myProfile.values=application.yaml` to inject application.yaml files

### reference existing application configuration from configMaps
If your cluster contains application.yaml or application.properties in a pre-stored configMap,
you can mount them by reference into your pod
```yaml
spring:
  config:
    myProfile:
      configMap:
        name: my-config-map
        key: my-key
        type: yaml
```
This can be a good option, if certain configuration should be shared, e.g. a config of all service urls.

### reference existing application configuration from secrets
As above, but for secrets
```yaml
spring:
  config:
    myProfile:
      secret:
        name: user-defined-config-map
        key: app.yml
        type: yaml
```

## Debug convenience to get started
The chart comes with a debug flag to easily start the pod in save mode to investigate further.
For example during development, this can be handy, if your application keeps failing to start
and you want to exec into a container to run it manually, check the mounted configuration or other things.

Of course, you could simply have a values.debug.yaml that you apply additionally, for these cases.
The main reason for this feature is convenience and the option to quickly toggle debug mode via
`helm upgrade my-release goatfryed/easy-spring-boot --reuse-values --set debugDeployment.enabled=true|false`

### start pod without a running application
By default, if debugDeployment is enabled, the pod will start with inactive health checks and simply wait for shutdown.
This allows you to exec into it and investigate in the cluster.

### use a debug profile configuration
You can also configure debug mode to use a different the of active profiles for advanced insight
```yaml
spring:
  profiles:
    active: 'local,database,k8sServices,logging,performance'
debugDeployment:
  overrideProfiles: 'local,database,k8sServices,chatty-logging'
```
This example assumes that all the mentioned profile configuration was configured
as explained in the [configuration section](#easy-profile-configuration-management) or built into the application.

## Configuration updates
Kubernetes doesn't update pods, when your mounted configuration or secrets change.
By default, a checksum of enabled application configuration defined in helm values is added to your pod.
This cases your pod to be recreated, when you update the configuration.

Check chart configuration options `deployment.recreatePodStrategy`.

If you mount external configuration, you can add your own label with a change date.

## Advanced configuration
The included configuration support is aimed at simple to medium requirements.
For more advanced use cases, consider [spring-cloud-config](https://spring.io/projects/spring-cloud-config)


# Contributing
Looking to contribute? Great! Check out [CONTRIBUTING.md](../../CONTRIBUTING.md)
