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
- [01/24 사용자 지정 유효성 검사](https://github.com/Jang2723/likelion-validation)
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
- `ConstraintValidator`: 실제 유효성 검사 로직이 담기는 클래스, 두 가지 파라미터 정의
  - 1. 어떤 어노테이션에 대해서 적용할 검증기인지
  - 2. 이 어노테이션이 붙어서 검증할 대상 데이터의 자료형
- `<EmailWhitelist, String>`: 검사할 어노테이션과 대상 타입
```java
  public EmailWhitelistValidator() {
    this.whiteList = new HashSet<>();
    this.whiteList.add("gmail.com");
  }
``` 
- 생성자에서 whitelist를 만들고
- `isValid`메서드: 입력받은 값이 whitelist에 포함되는지 검증
> 💡 `@Valid` 어노테이션 활용하는 경우, 어노테이션 적용 순서가 반드시 검증 순서와 일치한다는 보증이 없다!


- `value.split` 이전에 `value`가 null인지 검증하는 것이 안전
---
### 어노테이션에 설정 전달
- 어노테이션을 필드에 작성하는 단계에서 특정 값을 전달하여 검증 과정에 활용
- 검증에 사용할 정보를 전달할 추가 Element 정의
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailBlacklistValidator.class)
public @interface EmailBlacklist {
    String message() default "Email in blacklist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] blacklist() default {};
}
```
- 어노테이션 Element는 어노테이션을 추가하는 단계에서 인자로 전달 가능
```java
@EmailBlacklist(blacklist = "malware.good")
private String email;
```
- 이 추가한 인자를 활용하고자 한다면 `ConstraintValidator`의 `initialize` 메서드를 오버라이딩 해서 구현
- `initialize` 메서드의 인자로 필드에 적용된 어노테이션이 전달, 선던 당시에 활용한 인자의 값 확인도 가능
```java
public class EmailBlacklistValidator 
        implements ConstraintValidator<EmailBlacklist, String> {
    Set<String> blacklist;

    @Override
    public void initialize(EmailBlacklist annotation) {
        this.blacklist = new HashSet<>();
        this.blacklist.addAll(Arrays.asList(annotation.blacklist()));
    }
    ...
```