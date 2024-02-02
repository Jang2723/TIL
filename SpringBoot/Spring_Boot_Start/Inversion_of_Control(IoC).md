## Spring Boot Start
### Ioc Container
- 제어 반전을 구현하기 위해 Spring Framework에서 사용하는 것
> 💡 Container : 인스턴스의 생명 주기를 관리 및 생성된 인스턴스들에게 추가적인 기능 제공
---
### Inversion of Control(제어 반전)
- 클래스를 만드는 주체는 **개발자** 
- 그 클래스 인스턴스가 언제 만들어지는지 결정하는 주체는 **Spring Boot**

- Spring Boot는 내부적으로 요청을 받는 부분이 만들어져 있는 상태이고, 
- Spring Boot를 사용하는 개발자는 어떤 요청을 받았을 때 어떤 행동을 할지만 정해주면 되는 형태
- => 제어하는 주체가 역전이 된 상황!

> 💡 제어 반전(Inversion of Control) : 
> 개발자가 작성한 코드가 프레임워크가 제공하는 흐름에서 실행되는 형태의 디자인 패턴
----
### Bean
- IoC Container가 관리하는 객체
- 어떤 클래스를 Bean으로 정의하면, 해당 클래스를 필요로 하는 상황에 활용
- Bean 객체를 만들기 위해 필요한 의존성 (멤버 변수라던지)이 있다면, 해당 멤버 변수로서 활용할 수 있는 Bean 객체를 찾아 자동으로 할당
- 이를 Dependecy Injection(의존성 주입)이라고 칭함
- `BeanFactory` Container가 이를 생성하고 관리
----
### Spring의 Ioc Container
- Spring Framework는 이 제어 반전을 구현하기 위해 그 중심에 IoC Container를 가지고 있고,
- 정의해 둔 클래스를 필요한 상황에 맞게 객체로 생성,관리하며 
- 또 그 객체를 필요로 하는 다른 객체가 있을 때 이미 있는 객체를 다시 활용할 수 있도록 만들어짐
- 위의 특징으로 실제 로직을 담당하는 클래스를 `new` 키워드로 만들어야 하는 상황은 거의 오지 않음
> 💡 IoC Container는 Spring Container, 또는 ApplicationContext로도 지칭

- `@Controller`
  - 스프링 프로젝트가 실행될 때, IoC Container에서 Bean 객체로서 등록, 관리 준비
  - 요청이 왔을 떄 어떻게 처리해야 할지에 대한 정보를 가지고 있음
```java
package com.example.demo;

import org.springframework.stereotype.Controller;

@Controller
public class DemoController {
}
```
- `@SpringBootApplication`
  - 이 클래스를 기준으로 **Bean 객체**를 찾아서 관리
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```
- `SpringApplication.run(DemoApplication.class, args);`
  - run 메서드 실행
  - `DemoApplication`을 기반으로 Spring Boot를 실행
  - `DemoApplication`에 붙어있는 `@SpringBootApplication`어노테이션이 주변에 있는 클래스들을 자동으로 Bean 등록
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
        // 현재 실행중인 IoC Container를 반환한다.
		ApplicationContext applicationContext 
                = new AnnotationConfigApplicationContext(DemoApplication.class);
        // IoC Container가 관리하고 있는 Bean 객체들을 확인한다.
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}
		// SpringApplication.run(DemoApplication.class, args);
	}
}
```
- `DemoApplication`을 기준으로 Spring Boot를 실행
- `demoApplication`, `demoController` 출력
  - `DemoApplication`,`DemoController`클래스가 정상적으로 Bean으로 등록되었다는 의미
---
### `@SpringBootApplication`
- 실제로는 세가지 어노테이션이 합쳐진 어노테이션
1. `@SpringBootConfiguration(@Configuration)` 
   - IoC Container가 관리할 Bean객체
   - 자동 설정을 위한 Spring Boot 전용 어노테이션
   - (@Configuration)은 Spring 프레임워크 코어의 어노테이션
2. `@EnableAutoConfiguration`
   - 이 어노테이션이 붙은 Bean을 Spring Boot가 자동으로 관리
   - Spring Boot 어플리케이션의 필수 어노테이션
3. `@ComponentScan`
   - Bean 객체 검색 기준 설정 어노테이션
----
### `@Component`
- `@ComponentScan`이 `@Component`를 Scan한다는 의미
- IoC Container는 `@Component`가 붙은 클래스를 Bean 취급
- `@Component`로 취급되는 어노테이션
  - `@Controller`: 사용자의 입력을 처리하는 클래스
  - `@Service`: 비즈니스 로직을 담당하는 클래스
  - `@Repository`: 데이터베이스와 직접적인 소통을 담당하는 클래스

---
### Dependency Injection(의존성 주입)
- 프로젝트에 필요한 라이브러리, 어떤 클래스의 객체를 만들기 위해 필요한 속성 => **의존성**
> 💡 Dependency Injection(의존성 주입) : IoC Container가 Bean 객체를 만들면서 Bean이 필요로 하는 의존성을 자신이 관리하는
> Bean 객체 중 하나를 선정해 할당해 주는 것
```java
@Component
public class TempComponent {
    public void sayHello() {
        System.out.println("hello spring boot!");
    }
}
```
```java
@Controller
public class DemoController {
    private TempComponent tempComponent;

    public DemoController(TempComponent tempComponent) {
        this.tempComponent = tempComponent;
    }

    @RequestMapping("home")
    public String home() {
        tempComponent.sayHello();
        return "home.html";
    }
}
```
- 생성자를 추가하고 `TempComponent`를 인자로 받아 속성에 할당
- 생성자를 만들지 않고 실행한다면 `NullPointerException` 발생
