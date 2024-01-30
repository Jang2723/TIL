```java
package com.example.auth.article;

import com.example.auth.article.dto.ArticleDto;
import com.example.auth.article.entity.Article;
import com.example.auth.article.repo.ArticleRepository;
import com.example.auth.entity.UserEntity;
import com.example.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleDto create(ArticleDto dto){
        UserEntity writer = getUserEntity();

        // UserEntity 설정
        Article newArticle = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(writer)
                .build();

        // 저장
        newArticle = articleRepository.save(newArticle);
        return ArticleDto.fromEntity(newArticle);
    }

    private UserEntity getUserEntity() {
        // SecurityContextHolder에서 사용자 가져오기
        UserDetails userDetails =
                (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // 사용자 username 받아오기(userDetails.getUsername()), // UserEntity 회수 (userRepository.findByUsername(username)~NOT_FOUND));
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

```
- article을 생성하는 service 클래스 `ArticleService`
- `ArticleDto`를 이용해 article을 생성하고 `ArticleRepository`를 사용하여 데이터베이스에 저장

> 💡 Article entity가 아닌 ArticleDto를 사용하는 이유   
> 1. 레이어 간의 분리: 서비스 레이어와 컨트롤러 레이어 사이간에 데이터 전송을 위해 DTO(Data Transfer Object)를 사용하는 것이 일반적
>    - DTO는 비즈니스 로직과 관련이 없는 데이터 전송을 위한 객체, 엔티티가 아닌 컨트롤러와 서비스 간의 통신에 사용됨   
> 
> 
> 2. 데이터 은닉: DTO를 사용하면 클라이언트에 반환되는 데이터를 엔티티의 데이터를 엔티티 내부 표현과 분리 가능
>    - 클라이언트가 필요로 하는 최소한의 정보만을 전달, 불필요한 정보 노출 방지
> 
>
> 3. 버전 관리: API의 버전 관리에 유용
>    - DTO를 통해 반환되는 데이터의 구조를 변경하면 클라이언트 측에서 사용되는 데이터 구조를 변경하지 않고도 API의 버전 업데이트가 가능
>
> 
> 4. 속도 향상: 엔티티에는 떄로 많은 양의 데이터와 연관된 많은 연산이 포함될 수 있음
>    - DTO를 이용하면 이런 연산을 피할 수 있어서 데이터 전송 및 처리 속도를 향상 가능
> 
> 
> 5. 결합도 감소: DTO를 사용하면 클라이언트와 서버 간의 결합도 감소 
>    - 엔티티의 변경이 컨트롤러와 서비스에 영향을 미치지 않게 하기 위해
> 
> 
> => `ArticleDto`를 사용하여 데이터 전송을 처리함으로써 클라이언트와 서버간의 데이터 전송을 효율적으로 처리, 관리하여 관심사를 분리시킬 수 있음!


1. `@Service`: Spring에게 해당 클래스가 서비스 역할을 한다는 것을 알리는 어노테이션


2. `@RequiredArgsConstructor`: 필드 주입을 위한 생성자를 자동으로 생성


3. `private final ArticleRepository articleRepository`: Article 엔티티를 저장사고 검색하기 위한 `ArticleRepository` 인스턴스 주입


4. `private final UserRepository userRepository`: 사용자 정보를 저장하고 검색하기 위한 `UserRepository` 인스턴스 주입


5. `public ArticleDto create(ArticleDto dto)`: article을 생성하는 메서드
    - `ArticleDto`객체를 전달받아서 실제 article을 생성하고 그 결과를 `ArticleDto` 형태로 반환


6. `UserEntity writer = getUserEntity();`: 현재 사용자를 나타내는 `UserEntity`객체를 가져옴
    - `getUserEntity()`메서드
      1. `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`: 현재 인증된 사용자의 정보를 가져옴
         - Spring Security를 사용, 현재 사용자의 인증 정보를 얻을 수 있음, `Principal`객체는 현재 사용자의 인증 주체를 나타냄
      2. `(UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())`: 가져온 사용자의 정보를 `UserDetails`로 형변환
         - `UserDetails`는 Spring Security에서 제공하는 인터페이스, 사용자의 세부 정보를 나타냄, 이 인터페이스는 사용자의 `username`,`password`,`authorities`등을 포함
      3. `userDetails.getUsername()`: `UserDetails` 객체에서 사용자의 username을 가져옴 => 사용자의 식별자로 사용
      4. `userRepository.findByUsername(userDetails.getUsername())`: username을 기반으로 사용자 엔티티를 데이터베이스(repository)에서 조회
         - `userRepository`는 JPA를 사용하여 데이터베이스에 접근하고 사용자 정보를 조회하는데 사용
      5. `.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))`: `findByUsername()`메서드의 결과가 존재하지 않으면 `HttpStatus.NOT_FOUND` 상태 코드를 갖는 `ResponseStatusException`을 throw
         - 사용자를 찾을 수 없는 예외 발생시 처리하기 위한 방법
      - => 현재 인증된 사용자의 정보를 가져와서 해당 정보를 사용하여 데이터베이스에서 사용자를 조회하고, 조회된 사용자의 엔티티를 반환하는 역할을 수행


7. `Article newArticle - Article.builder()...`: 새로운 article을 생성


8. `newArticle = articleRepositoru.save(newArticle);`: 새로 생성한 article을 데이터베이스(repository)에 저장


9. `return ArticleDto.fromEntity(newArticle);`: 데이터베이스에 저장된 새로운 article을 `ArticleDto`형태로 변환하여 return