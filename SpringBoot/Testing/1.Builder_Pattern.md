## Security
### Builder 패턴
#### Builder 패턴의 기본 개념
- 객체 생성 과정을 단계별로 나누어 관리, 복잡한 객체의 생성을 간소화하고 명확하게하는 것
- 생성자에 많은 매개변수가 필요한 경우에 유용
- 객체 생성 과정에서 필요한 매개 변ㄴ수만 명시적으로 설정 가능
---
#### Builder 패턴을 활용하지 않고 객체를 생성하는 방법 2가지
- User Class
```java
public class User {
		//필드
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String status;
		// 생성자
		// Getter
		// Setter
		
}
```
1. 전통적인 생성자를 이용한 객체 생성
- `User` 클래스에 생성자와 Getter 추가
```java
// 생성자
public User(Long id, String username, String password, String email, String phone, String firstName, String lastName, String status) {
this.id = id;
this.username = username;
this.password = password;
this.email = email;
this.phone = phone;
this.firstName = firstName;
this.lastName = lastName;
this.status = status;
}

// Getter
public Long getId() {
return id;
}

public String getUsername() {
return username;
}

//...
```
- `BuilderMain`클래스의 메인 메서드 안에 코드 추가
```java
User newUser1 = new User(
    null, "subinID", null, "subin@gmail.com", null, "SUBIN", "JANG", null
);
```
2. JavaBean 스타일을 이용한 객체 생성
- `User`클래스에 생성자와 Setter추가
```java
// 생성자
public User() {}

// Setter
public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
        //...
```
- `BuilderMain`클래스에 메인 메서드 안에 코드 추가
```java
User newUser2 = new User();
newUser2.setUsername("subinId");
newUser2.setEmail("subin@gmail.com");
// ...
```
두 가지 방식의 문제점
- 모든 필드를 매개변수로 전달받는 전통적인 생성자를 사용하면,
  - 객체 생성시 필요한 모든 값들을 순서에 맞게 넣어야 하고 사용하지 않는 필드에도 `null`값을 넣어 주어야 함
- 빈 생성자를 사용해 객체를 생성하고 Setter메서드를 통해 필드 값을 설정하면, 모든 속성들이 Setter를 가지고 있어야 함
   
---
#### Builder 패턴 적용
1. `User`클래스 안에 `UserBuilder`클래스 생성
```java
// User.java
public static class UserBuilder {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String status;

    // 기본생성자
    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder email(String email){
        this.email = email;
        return this;
    }

    public User build() {
        return new User(
                this.id,
                this.username,
                this.password,
                this.email,
                this.phone,
                this.firstName,
                this.lastName,
                this.status
        );
    }
}
```
2. `BuilderMain`클래스에서 Builder패턴의 사용
```java
User.UserBuilder userBuilder = new User.UserBuilder();

User.UserBuilder a = userBuilder.id(1L);
User.UserBuilder b = a.username("subinId");
User.UserBuilder c = b.email("subin@gmail.com");

User newUser = c.build();
```
- 간결하게 만들수도 있음
```java
User.UserBuilder userBuilder = new User.UserBuilder();

User newUser = userBuilder
        .id(1L)
        .username("subinId")
        .email("subin@gmail.com")
        .build();
```
3. build()메서드 활용
- `User`클래스 내에 `UserBuilder`타입의 새로운 인스턴스를 생성하고 반환하는 `builder()`메서드 생성
```java
public static UserBuilder builder(){
        return new UserBuilder();
    }
```
- `BuilderMain`클래스에서 `User.builder()`를 호출하여 `UserBuilder`인스턴스를 생성
```java
User newUser = User.builder()
				.id(1L)
                .username("subinId")
                .email("subin@gmail.com")
                .build();
```
4. Lombok의 `@Builder` 사용
- `User`클래스에 `@Builder`어노테이션을 적용하면 Lombok이 자동으로 Builder 클래스를 생성
```java
package com.example.contents.builder;

import lombok.Builder;

@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String status;

}
```
- 객체 생성
```java
package com.example.contents.builder;

public class BuilderMain {
    public static void main(String[] args) {
        
        User newUser = User.builder()
                .id(1L)
                .username("subinId")
                .email("subin@gmail.com")
                .build();
    }
}
```
- `User.builder()`를 호출하여 Builder 인스턴스 생성
- 체이닝 방식으로 필요한 메서드들을 호출하여 필드 값을 설정
- `.build()`메서드를 호출하여 최종적으로 `User`객체를 생성