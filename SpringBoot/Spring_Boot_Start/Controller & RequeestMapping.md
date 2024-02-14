## Spring Boot Start
### Controller & RequestMapping
> [Controller & RequestMapping Github](https://github.com/Jang2723/likelion-mvc/blob/main/src/main/java/com/example/demo/DemoController.java)
- `@Controller`
  - URL에 따른 요청을 처리하는 메서드를 담아두는 클래스임을 나타낸다.
- `@RequestMapping`
  - 어떤 URL 요청에 대해 실행되는 메서드임을 나타낸다.

```java
@Controller
public class DemoController{
    @RequestMapping("home")
    public String home() {
        return "home.html";
    }
}
```
- 실행 후 `http://localhost:8080/home` 이동 -> 결과 확인