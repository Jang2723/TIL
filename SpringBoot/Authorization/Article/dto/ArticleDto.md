```java
package com.example.auth.article.dto;

import com.example.auth.article.entity.Article;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    // private ArticleWriterDto writer;
    @Setter
    private String writer;

    public static ArticleDto fromEntity(Article entity){
        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter().getUsername())
                .build();
    }

}
```
- Article 데이터를 다루기 위해 DTO를 생성
- static factory생성
- writer는 UserEntity의 username을 사용할 것이기 때문에
  - Article Entity의 writer를 참조하는 `.getWriter`에
  - UserEntity를 참조하는 `.getUsername`을 덧붙여서 사용

```java
//ArticleWriterDto.java
package com.example.auth.article.dto;

public class ArticleWriterDto {
    private String username;
    private String grade;

}
```
- writer Dto를 따로 만들어서 관리할 수도 있지만 이번에는 그렇게 하지 않