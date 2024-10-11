# Dirty Manual testing
collection of commands to test things and verify manually.

Test concept to be done

## array profile
Check deployment container env
```shell
helm template --debug example ../charts/spring-boot/ -f arrayProfile/values.yaml > arrayProfile/out.actual.yaml
```

## array profile
Mount two config maps and activate profiles automatically

```shell
helm template --debug example ../charts/spring-boot/  \
  -f configMap/values.yaml \
  --set-file spring.config.local.values=configMap/application.yaml \
  > configMap/out.actual.yaml
```

