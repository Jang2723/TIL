## Validation
유효성 검사란?   
사용자가 입력한 데이터가 허용된 형태인지 검사하는 과정

### spring-boot-starter-validation
Jakarta Bean Validation   
- 유효성 검증을 위한 기술 명세
- 어떤 항목이 어떤 규칙을 지켜야 하는지를 표시하는 기준

Hibernate Validation
- Jakarta Bean Validation를 토대로 실제로 검증해주는 프레임워크   
- `implementation 'org.springframework.boot:spring-boot-starter-validation'`
---
### `@Valid`
```java
// 간단한 UserDto
@Data
public class UserDto{
    private Long id;
    @NotNull(message = "username is required!")
    private String username;
    @Eamil
    private String email;
}
```
`@NotNull(message = "username is required!")` -> 제약 사항을 명시하는 Annotation   
- username 필드는 비어있어서는 안됨
- message = "~" 통해 출력될 메세지를 수정할 수 있음

```java
@RestController
public class UserController{
    @PostMapping("/validate-dto")
    public String validateDto(
            // 이 데이터는 입력을 검증해야 한다.
            @Valid
            @RequestBody
            UserDto dto
    ){
        log.info(dto.toString());
        return "done";
    }
}
```
- `@Valid` : 해당 인자에 대한 유효성 검증
- `username` : Dto를 참고하면 Null이면 암됨
- `email` : @가 들어간 이메일 형식이어야 함
- 둘다 형식을 지키지 않는 다면 400 에러 발생

----
### 사용 사능한 어노테이션
> 공식문서 - https://beanvalidation.org/2.0/spec/#builtinconstraints

- 간단한 유효성 검증: [github(springboot-validation)](https://github.com/Jang2723/likelion-validation)
- `@Size` : 크기 지정 (ex. @Size(min = 8))
- `@Email` : 이메일 형식
- `@Min` : 최소 크기 지정
- `@Future` : 미래 날짜를 나타내는 Java 클래스('YYYY-MM-DD')
- `@NotNull` : NULL이 될 수 없음
- `@NotEmpty` : 문자열, 리스트 등이 비어있을 수 없음, size가 0이 아니면 됨(공백 가능)
- `@NotBlank` : 문자열이 공백이 아님, 공백으로 표햔되는 문자 외의 문자가 존재
----
### 일부 유효성 검사 (@Validated)
```java
// UserPartialDto
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialDto {
    // 두가지는 회원가입 단계에서 반드시 첨부해야 하는 데이터
    // 단 회원 정보 업데이트 단계에서는 반드시는 아님
    @Size(min = 8, groups = MandatoryStep.class)
    @NotNull(groups = MandatoryStep.class)
    private String username;
    @Size(min = 10, groups = MandatoryStep.class)
    @NotNull(groups = MandatoryStep.class)
    private String password;

    // 두가지는 회원가입 완료 후 추가 정보 기입 단계에서 첨부하는 데이터
    // 단, 추가정보 기입시에는 반드시 포함해야 한다.
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phone;
}
```
- 상황에 따라 다른 검증을 하고 싶을 경우 `@Validated` 사용
```java
   // /validate-man으로 요청 할 때는
    // username과 password에 대한 검증만 진행하고 싶다.
    @PostMapping("validate-man")
    public String validateMan(
        @Validated(MandatoryStep.class)
        @RequestBody
        UserPartialDto dto
    ){
        log.info(dto.toString());
        return "done";
    }
```
- groups라는 패키지를 만들고 `MandatoryStep` 이름의 인터페이스 생성
- 반드시 검증하고 싶은 것에만 `groups = MandatoryStep.class` 를 붙임
- 반드시 검증할 것이기 때문에 `@NotNull(groups = MandatoryStep.class)`이 붙어있어야 함
- 패키지이름 = 인터페이스이름.class
- MadatoryStep에는 아무 내용도 적혀있지 않음
----
### 유효성 검사 실패 시 응답
`@Validated` 예외
  - @Validated는 Spring 프레임워크에서 그룹 기반의 검증을 처리, 주로 메서드의 파라미터에 적용
  - ConstraintViolationException은 주로 Bean Validation에서 발생하는 예외로, 그룹 기반의 검증 오류를 나타냄
  - @Validated로 검증한 경우에도 MethodArgumentNotValidException이 발생 가능
    - 예를 들어, 매개변수에 바인딩된 객체가 아닌 경우, MethodArgumentNotValidException이 발생


`@Valid` 예외
- @Valid는 주로 Spring MVC에서 사용되며, 주로 컨트롤러 메서드의 메서드 인자에 적용
- MethodArgumentNotValidException은 주로 Spring에서 검증 오류가 발생할 때 발생하는 예외
- 주로 Bean Validation 검증 오류 또는 @RequestBody, @RequestPart 등의 바인딩 오류 등이 이에 해당


> `MethodArgumentNotValidException`을 대상으로 예외 처리(400에러)
```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(
            final MethodArgumentNotValidException exception
    ) {
        Map<String, Object> errors = new HashMap<>();
        // 예외가 가진 데이터를 불러오기
        List<FieldError> fieldErrors
                = exception.getBindingResult().getFieldErrors();

        // 각각의 에러에 대해서 순회하며
        for (FieldError error: fieldErrors) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }
```
- 주로 `@Valid` 어노테이션이 붙어있을 때 해당 매개변수에 대한 검증 오류시 발생
- `@ExceptionHandler(MethodArgumentNotValidException.class)` -> ( ) 안의 예외 발생시 처리할 것이라는 뜻
- `@ResponseStatus(HttpStatus.BAD_REQUEST)` -> 사용자가 잘못 입력한 경우(BAD_REQUEST)
- `List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();` -> 예외가 담고 있는 데이터를 불러오기


----
> `ConstraintViolationException`을 대상으로 예외 처리(500에러)
 ```java
    @ExceptionHandler(ConstraintDeclarationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintException(
            final ConstraintViolationException exception
            ){
        Map<String, Object> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations
                = exception.getConstraintViolations();
        for (ConstraintViolation<?> violation: violations){
            String property = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(property , message);
        }
        return errors;
    }
```
- 주로 `@Validated` 어노테이션이 붙어있을 때 해당 메서드 인자의 유효성 검증과 관련된 제약 위반시 발생
- `@ExceptionHandler(ConstraintDeclarationException.class)` -> ( ) 안의 예외 발생시 처리할 것이라는 뜻
- `@ResponseStatus(HttpStatus.BAD_REQUEST)` -> 사용자가 잘못 입력한 경우(BAD_REQUEST)
- `Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();` -> 인자를 `?`로 두면 어떤 인자가 와도 사용 가능
