## Validation
### 사용자 지정 유효성 검사
만들어 볼 수 있는 것
- 이메일 검사기
  - annotation
    - EmailWhitelist
    - EmailBlacklist
  - EmailWhitelistValidator
  - EmailBlacklistValidator
- 요일을 문자로 표현하기 (mon, tue, wed, thu, fri, sat, sun)
- 주민등록번호 검사기
- 비밀번호 강도측정 (zxcvbn)


### Annotation 만들기
- [01/24 사용자 지정 유효성 검사](https://github.com/Jang2723/Springboot-validation)
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// EmailWhitelist 인터페이스
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailWhitelistValidator.class)
public @interface EmailWhitelist{
  String message() default "Email not in whitelist";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
```
`@interface`를 이용해서 Annotation 정의   
`@Target`: Annotation이 첨부될 수 있는 영역   
`@Retention`: Annotation이 언제까지 붙어있어야 할지(Compile, Runtime 등)   
`@Document`: Annotation이 문서에도 붙어있어야 하는지
`@Constraint`: Annotation의 제약조건 설정

```java
// EmailWhitelistValidator

public class EmailWhitelistValidator
        implements ConstraintValidator<EmailWhitelist, String> {
  private final Set<String> whiteList;
  public EmailWhitelistValidator() {
    this.whiteList = new HashSet<>();
    this.whiteList.add("gmail.com");
  }
  @Override
  public boolean isValid(
          // value: 실제로 사용자가 입력한 내용이 여기 들어온다.
          String value,
          ConstraintValidatorContext context
  ) {
    // value가 null인지 체크하고, (null이면 false)
    if (value == null) return false;
    // value에 @가 포함되어 있는지 확인하고 (아니면 false)
    if (!value.contains("@")) return false;
    // value를 @ 기준으로 자른 뒤, 제일 뒤가 'this.whiteList'에
    // 담긴 값 중 하나인지 확인을 한다.
    String[] split = value.split("@");
    String domain = split[split.length - 1];
    return whiteList.contains(domain);
  }
}
```
- `ConstraintValidator`: 실제 유효성 검사 로직이 담기는 클래스
- `<EmailWhitelist, String>`: 검사할 어노테이션과 대상 타입
```java
  public EmailWhitelistValidator() {
    this.whiteList = new HashSet<>();
    this.whiteList.add("gmail.com");
  }
``` 
- 생성자에서 whitelist를 만들고
- `isValid`메서드: 입력받은 값이 whitelist에 포함되는지 검증