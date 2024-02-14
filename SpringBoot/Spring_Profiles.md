## Spring Profiles
> 💡 github [/src/main/resources](https://github.com/Jang2723/likelion-Config/tree/main/src/main/resources)   


**Spring Profiles**      
- 다양한 설정을 Profile이라는 단위로 나눠서, 사용할 설정을 어플리케이션 실행시 결정할 수 있게 해주는 Spring의 기능
- 어플리케이션이 실행되는 환경에 따라 다른 설정을 적용하고 싶을 떄 사용할 수 있는 기능
    - 개발 단계에서 사용할 데이터베이스와 테스트 코드용 데이터베이스를 나누거나
    - 서비스 할 때 로그를 줄이고 싶은 경우
---
**`application-{profile}yaml`**
1. 사용하고 싶은 profile 이름을 정하교(`dev`,`prod`,`test` 등)
2. 그 이름이 포함된 `application-{profile}.yaml` 파일 생성

**기본 profile**
- `application.yaml` 설정은 기본으로 실행
- 어플리케이션 실행시 profile을 정하지 않으면 실행할 profile은 `spring.profiles.default`
  - `application.yaml`에 추가할 수 있는 `spring.profiles.default`를 이용해 기본값으로 사용할 profile을 지정할 수 있음
```yaml
# application.yaml
spring:
  profiles:
    default: dev
  mvc:
    # /static/ 으로 시작하는 요청에 대해 정적 파일 서빙
    static-path-pattern: /static/**
  web:
    resources:
      # 정적 파일 탐색 장소
      static-locations: file:media/,classpath:/static
  main:
    banner-mode: off
```
```yaml
# application-dev.yaml
spring:
  datasource:
    url: jdbc:sqlite:db.sqlite
    driver-class-name: org.sqlite.JDBC
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
logging:
  config: file:logback-spring.xml
  level:
    root: ${LOG_LEVEL:info}  # 환경변수 활용 가능
```
- 이렇게 설정하여 실해할 경우`application-dev.yaml`의 설정을 사용하되,
- 지정하지 않은 설정 중 `application.yaml`에 지정된 설정은 가져가서 사용


**`spring.profiles.active`**
- Spring Boot 실행 시 전다랗여 사용할 profile 선택
- `application.yaml`에 포함할 수 있지만 `java` 명령어로 `jar` 파일을 실행할 때 첨부도 가능
```text
java -Dspring.profiles.active=test -jar build/libs/contents-0.0.1-SNAPSHOT.jar
```
**`@Profile`**
- 상황에 따라 Bean객체도 특정 profile에서만 만들어지게 작성 가능
- 두개의 `Configuration`객체 생성
```java
// DevConfig
@Configuration
@Profile("dev")
public class DevConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean("dcn")
    public String driverClassName() {
        return driverClassName;
    }
}


// TestConfig
@Configuration
@Profile("test")
public class TestConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean("dcn")
    public String driverClassName() {
        return driverClassName;
    }
}
```
- 내용은 같지만 `@Profile`어노테이션의 인자가 다름
- `DevConfig`는 profile이 `dev`인 경우만 생성
- `TestConfig`는 profile이 `test`인 경우만 생성