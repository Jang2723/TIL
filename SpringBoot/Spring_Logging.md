## Spring Logging 
> 💡 github : [Spring-Config](https://github.com/Jang2723/likelion-Config)
### Logging 이란?
- 우리의 코드가 일으킨 결과를 확인할 때 만이 쓰던 것
  - `System.out.println` => 코드를 실행한 터미널에 인자 출력


- 어플리케이션이 실행중 일 때 일어난 일 파일로 기록 => "log" 
  - Logging : 로그를 남기는 행위
  - `Logger` : 로그를 작성하기 위해 사용하는 객체

---
### Spring Boot 로깅
**Simple Logging Facade 4 Java(Slf4j)**   
- Java에서 로그를 작성하는 방법을 통일하는 프레임워크
- 자기 자신을 로그를 남기는 프레임워크가 아님
- 다양한 로그 프레임워크를 사용하는 방법의 Facade(디자인 패턴)일 뿐!


**Logback**
- 현재 Spring Boot에서 기본으로 사용하는 Logging 프레임워크
- XML을 이용해 설정

---
### Logger 동작 알아보기
- 간단한 컨트롤러를 만들고 내부에 동작 남겨보기
```java
//@Slf4j
@RestController
public class TestController {
    private static final Logger log
            = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/log")
    public void logTest() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("A INFO Message");
        log.warn("A WARN Message");
        log.error("A ERROR Message");
    }
}
```
- `LoggerFactory.getLogger` : Slf4j에서 로거를 만들어 넘겨주는 메서드, 어떤 클래스에서 로그를 남겼는지 기록하기 위해 `TestController.class` 인자로 전달
  - `@Slf4j` : ` private static final Logger log = LoggerFactory.getLogger(TestController.class);` 이 부분을 자동으로 만들어주는 Lombok 어노테이션

--- 
### Log Level
로그를 남길 때 정할 수 있는 중요도, 중요도에 따라 출력되는 로그 조절 가능
- TRACE : 가장 낮은 단계의 로그, 아주 작은 변화의 로그를 남기는 레벨
- DEBUG : TRACE 보다는 조금 덜 구체적인, 개발자의 디버깅을 도와주는 로그를 남기는 레벨(구체적인 내부 플로우에 대해 기록을 남김)
- INFO : 어플리케이션이 실행중일 떄 정보 제공의 목적으로 남기는 레벨
- WARN : 어플리케이션이 실행중일 때 아직 문제가 되지는 않지만 악영향이 있을 수 있는 로그를 남기는 레벨
- ERROR : 어플리케이션이 정상적으로 동작하지 못한 상황에 대한 로그를 남기는 레벨

> 💡 설정한 레벨보다 더 중요한 레벨의 로그만 작성   
> `TRACE` => `DEBUG` => `INFO` => `WARN` => `ERROR`
- 설정된 레벨이 `INFO`라면 `INFO`, `WARN`, `ERROR` 로그만 작성
- 로그 레벨만 간단하게 조정하고 싶을 경우 `application.yaml` 활용
```yaml
logging:
  level:
    root: trace
```
---
### Logback 설정
특정 파일에 로그를 남기거나, 특정 날짜 까지의 기록만 남겨두고 싶을 때
- Spring Boot는 resource의 `logback.xml` 또는 `logback-spring.xml`파일을 자동으로 사용
  - [logback-spring.xml 예시](https://github.com/Jang2723/Spring-Config/blob/main/src/main/resources/logback-spring.xml)    



**`<appender>`**
- Logback에서 제공하는 Appender 인터페이스를 설정하기 위한 요소
- `ConsoleAppender` : 터미널 화면에 로그를 남기기 위한 Appender
- `FileAppender` : 특정 파일에 로그를 남기기 위한 Appender
- `RollingFileAppender` : 특정 조건에 따라 파일 갯수 및 크기를 유지하면서 로그를 남기기 위한 Appender


**`<layout> & <recoder>`**
- 출력되는 로그의 형식을 정의하기 위한 요소
- `<Layout>` : 특정 문자열로 바꾸기 위해 사용
- `<Encoder>` : 파일에 작성 가능한 `byte[]`형태로 바꾸기 위해 사용
- 둘 다 `<Pattern>` 요소를 가짐
  - 실제 로그의 출력을 조정하기 위한 부분
  - `%d` : 로그가 작성된 날짜
  - `%p` : 로그 레벨
  - `%C` : 로그를 남긴 클래스
  - `%t` : 로그를 남긴 쓰레드
  - `%m` : 로그 메시지
  - `%n` : 개행 문자


**`<file>`**
- `FileAppender`와 `RolliingFileAppender` 는 파일의 형태로 로그를 남기기 때문에, 어떤 파일에 로그를 남길지에 대한 설정


**<rollingPolicy>**
- 서버에 남아있는 록를 일정한 양만 남기는 용도
```xml
<rollingPolicy
        class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
  <fileNamePattern>${LOGS}/archived/rolling-file-log-%d{yyyy-MM-dd_HH-mm-ss}.%i.log
  </fileNamePattern>
  <maxFileSize>100MB</maxFileSize>
  <maxHistory>10</maxHistory>
  <totalSizeCap>1GB</totalSizeCap>
</rollingPolicy> 
```
- `maxFileSize` : 로그 파일 당 크기 100MB
- `maxHistory` : 이 Appender가 유지하는 총 록 파일의 갯수
- `totalSizeCap` : 모든 로그 파일의 크기 합


**`<root>` & `<logger>`**
- `LoggerFactory`로 만들어진 로거가 어떤 Appender를 사용할지 정하는 설정
- `<root>` : 전체 어플리케이션에 적용시킬 Appender 결정
- `<logger>` : `LoggerFactory`를 통해 생성한 로거에 따라, 클래스나 패키지 단위로 적용할 Appender 설정
- 각 로거별로 레벨을 달리 설정 가능
```xml
<!-- LOG everything at INFO level -->
<root level="info">
  <appender-ref ref="RollingFile" />
  <appender-ref ref="Console" />
</root>

<!-- LOG "org*" at WARN level -->
<logger name="org" level="warn" additivity="false">
  <appender-ref ref="Console" />
  <appender-ref ref="File" />
</logger>

<!-- LOG "com.example.contents*" at TRACE level -->
<logger name="com.example.contents" level="trace" additivity="false">
  <appender-ref ref="RollingFile" />
  <appender-ref ref="Console" />
  <appender-ref ref="File" />
</logger>
```
**`application.yaml` 에서 설정 파일 지정**
- 어떤 Logback 설정을 사용할지 전달
```yaml
logging:
  config: file:logback-spring.xml
```