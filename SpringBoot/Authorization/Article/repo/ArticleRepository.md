```java
package com.example.auth.article.repo;

import com.example.auth.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

```
- Spring Data JPA에서 제공하는 `JpaRepository` 인터페이스를 상속받음
  - 기본적인 CRUD 기능을 제공
- `<Article, Long>`은 `JpaRepository`를 구현할 때 사용될 엔티티 클래스와 해당 덴티티의 기본키 타입을 지정