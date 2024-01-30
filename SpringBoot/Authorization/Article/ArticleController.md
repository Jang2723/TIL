```java
package com.example.auth.article;

import com.example.auth.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;
    
    @PostMapping
    public ArticleDto create(
            @RequestBody
            ArticleDto dto
    ) {
        return service.create(dto);
    }
    
}
```
- 간단하게 `@PostMapping`으로 create 기능만 구현


- `@PostMapping`: 해당 메서드가 HTTP POST 요청이면 처리


- `public ArticleDto create`: `ArticleDto`타입의 객체를 생성하는 역할 
  - 즉, 새로운 article을 생성하는 역할


- `@RequestBody ArticleDto dto`: HTTP 요청 본문(body)에 포함된 데이터를 `ArticldDto`객체로 변환하여 메서드 파라미터로 전달
  - 클라이언트가 JSON 또는 XML 형식으로 데이터를 전송하면 Spring이 해당 데이터를 `ArticldDto`객체로 자동 변환하여 메서드에 전달


- `return service.create(dto)`: service 객체의 `create`메서드를 호출하여 클라이언트가 전송한 `ArticleDto`객체를 사용하여 실제로 article을 생성
  - 이후 생성된 article에 대한 정보가 담긴 `ArticleDto` 객체를 반환