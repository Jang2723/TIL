## Spring Beans
### Beans
- IoC Container가 관리하는 객체
- Spring Framework는 IoC Container와 Dependency Injection으로 저희 프로젝트의 코드 및 클래스가 유연하게 역할분담 할 수 있도록 도움
- Spring은 클래스가 Bean 객체로 등록될 수 있도록 다양한 어노테이션 제공
> 💡 `org.springframework.streotype` 패키지 안에 위치, Spring Stereotype Annotation이라고 부름
> - `@Controller`
> - `@Component`
> - `@Service`
> - `@Repository`

---
### Spring Beans
구현된 기능상의 차이도 있지만 주된 목적은 역할의 표시
- 해당 클래스가 어떤 역할인지를 명확히 확인 가능
- 각 클래스가 담당하는 역할을 개념적으로 구분 가능
- Annotation 기반 관점 지향 프로그래밍 구현에 도움
> 💡 실제 구현보다 역할적인 구분에 초점!

---
### `@Component`
- 가장 기초가 되는 Annotation
- `@ComponentScan(@SpringBootApplication)`의 대상
- 직접적인 서비스의 비즈니스 로직에서는 벗어난 기능들
  - 외부 API 활용, 특수 목적의 공유 가능한 기능들을 만들고자 할 때 등
- 또 다른 Annotation을 쓰기 애매할 때 사용


### `@Controller`
- MVC 패턴의 Controller, 사용자 입력을 처리하기 위한 Annotation
- `@RequestMapping`과 함께 활용하여 사용자가 사용할 수 있는 인터페이스를 구현


### `@Service`
- 서비스의 주요 흐름, 비즈니스 로직을 담당하는 요소를 지칭하는 Annotation
- 여러 요소들의 기능을 조합해 실제 서비스를 제공하기 위한 주요 기능 담당
  - `@Controller`로 부터 입력을 받고
  - `@Repository`로 데이터 받고
  - 이를 바탕으로 결정을 전달


### `@Repository`
- 데이터베이스와 직접적으로 소통하는 요소를 지칭하는 Annotation


### `@Configuration` & `@Bean`
- 프로젝트 내부에서 활용하기 위한 설정들을 담고 있는 Annotation
- `@Bean` : `@Configuration` 내부에서 정의한 메서드의 결과를 Bean 객체로 관리하고 싶을 때 사용
  - 외부 라이브러리 객체를 Bean으로 등록하는데 활용 가능