## Validation
### 예외처리
### throw new ResponseStatusException();
어디서든 간편하게 사용할 수 있는 예외   
별도의 작업 없이 간편하게 사용 가능   
똑같은 코드를 여러번 반복하게 되는 단저밍 존재
```java
public UserDto  updateUser(Long id, UserDto dto){
    Optional<UserEntity> optionalUser = repository.findById(id);
    if (optionlaUser.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
}
```
- `optionalUser`가 비어 있으면 `NOT_FOUND` 예외 발생

----
###  @ExceptionHandler
controller 내부 예외 처리
```java
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
        final IllegalArgumentException exception){
    return ResponseEntity
        .badRequest()
        .body(new ErrorResponseDto(exception.getMessage()));
        }
```
- controller 내부에서 지정한 예외가 발생하면 실행되는 메서드
- RequestMapping 메소드 처럼 응답
---
### @ControllerAdvice & @RestControllerAdvice
프로젝트 전역 예외 처리
```java
@RestControllerAdvice
public class UserControllerAdvice{
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
            final IllegalArgumentException exception
    ){
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDto(exception.getMessage()));
    }
}
```
- `@ExceptionHandler` 를 모아두기 위한 componenet의 일종
- `@ControllerAdvice`와 `@RestControllerAdvice`는 `@Controller`와 `@RestController`와 같은 관계
---
### 커스텀 예외 활용하기
- 프로젝트가 커지면 일관성 있는 예외처리를 위해 예외를 직접 정의
- 이름에 상황에 대한 정보를 바로 표현할 수 있으며,
- 상속 관계를 활용해 하나의 `ExceptionHandler`에서 동시에 예외처리가 가능
```java
// 400 응답을 발생시키기 위한 부모 추상 예외
public abstract class Status400Exception extends RuntimeException{
    public Status400Exception (String message){
        super(message);
    }
}
```