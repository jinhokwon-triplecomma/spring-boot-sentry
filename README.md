##### sentry 환경변수 설정

* SENTRY_AUTH_TOKEN
* SENTRY_DSN 

#### build.gradle

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
}

plugins {
  id "io.sentry.jvm.gradle" version "4.3.0"
}

sentry {
  // Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
  // This enables source context, allowing you to see your source
  // code as part of your stack traces in Sentry.
  includeSourceContext = true

  org = "home-z4r"
  projectName = "java-spring-boot"
  authToken = System.getenv("SENTRY_AUTH_TOKEN")
}
```

#### application.yml
```yaml
sentry:
  dsn: https://a4d5c....dc4@o...72.ingest.sentry.io/450674...0944
  # Set traces-sample-rate to 1.0 to capture 100% of transactions for performance monitoring.
  # We recommend adjusting this value in production.
  traces-sample-rate: 1.0
```

#### ControllerExceptionHandler.java
Sentry 로그 수집을 위해서, 에러 핸들러에 `Sentry.captureException` 메소드를 추가합니다.
```java
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {
            RuntimeException.class
    })
    public ResponseEntity<BaseResponse> runtimeException(Exception ex, WebRequest request) {
        Sentry.captureException(ex);
        return new ResponseEntity<>(BaseResponse.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        Sentry.captureException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
```


## Reference
