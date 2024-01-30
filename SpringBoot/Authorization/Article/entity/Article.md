```java
package com.example.auth.article.entity;

import com.example.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    private String content;

    @Setter
    @ManyToOne
    private UserEntity writer;
}
```
- Article writer를 UserEntity의 username으로 설정할 예정