## MappedSuperclass
- JPA를 활용해 데이터베이스를 다룰떄, 하나의 테이블에 대한 정보를 정의하기 위해 `@Entity` 어노테이션을 활용해 클래스 생성
- 이중 일부 속성들은 반복해서 등장
- ex) `Article`과 `Comment` Entity의 `id`속성은 항상 추가 되었으며 다른 여러 Entity에서도 활용할 가능성이 높음
- Java는 객체지향 프로그래밍 언어로 다른 클래스를 상속받을 수 있음
  - 이 특성을 이용해 여러개의 Entity가 공유하는 속성들을 모아서 하나의 클래스를 생성 => `@MappedSuperclass`

---
### `@MappedSuperclass`
- 컬럼 정보를 나타내는 클래스에 붙여 자신에게 정의된 컬럼을 상속받아 사용할 수 있게 해주는 어노테이션
- ex) 모든 Entity가 가질 `Long id`
- 이런 속성을 가지는 **추상 클래스** `BaseEntity` 생성 후 `@Entity` 대신 `@MappedSuperclass`적용
```java
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```
- `@MappedSuperclass`로 생성된 클래스는 다른 클래스가 상속받는 것이 목표이기 때문에 `ddl-auto: create`로 설정하더라도 실제 테이블이 생성되지는 않음

```java
@Entity
public class Article extends BaseEntity {
    private String title;
    private String content;
    private String writer;
}
```
- `ArticleService`또는 테스트 코드를 이용해 `Article` 생성 테스트
- `id`가 `null`이 아니면 성공
```java
@Test
@DisplayName("id 정상생성 테스트")
public void checkAutoGen() {
    // given
    Article article = Article.builder()
            .title("Test Title")
            .content("Test Content")
            .writer("Writer")
            .build();

    // when
    article = articleRepository.save(article);

    // given
    assertNotNull(article.getId());
}
```

---
### `@EnableJpaAuditing`
- 생성일, 수정일 같은 정보를 저장하고 싶을 때 사용하는 어노테이션
- Spring Boot의 기본 설정으로는 실행되지 않기 때문에 `@SpringBootApplication`이나 Bean으로 등록될 수 있는 `@Configuration`클래스에 적용
```java
@Configuration
@EnableJpaAuditing
public class JpaConfig { }
```
- `LocalDateTime` 자료형으로 `createdAt`속성과 `updatedAt` 속성 추가
```java
// 다른 Entity가 상속받아서 내가 가진 속성 정보를 포함시키고 싶을 때
// 상속 받는 Entity 클래스 임을 나타내는 Annotation
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createAt;
    @CreatedDate
    private LocalDateTime updateAt;
}

```
- `@EntityListeners(AuditingEntityListener.class)` : 이 엔티티에 변경 사항이 발생할 때 그 내용을 관찰할 클래스를 지정
- `@CreatedDate` : 해당 속성이 나타내는 컬럼은 데이터의 생성일자를 나타내는 컬럼
- `@LastModifiedDate` : 해당 속성이 나타내는 컬럼은 데이터의 마지막 수정일자
- 아래는 테스트 코드
```java
@Test
@DisplayName("생성일 / 수정일 정상생성 테스트")
public void checkAutoGenTime() {
    // given
    Article article = Article.builder()
            .title("Test Title")
            .content("Test Content")
            .writer("Writer")
            .build();

    // when
    article = articleRepository.save(article);

    // given
    assertNotNull(article.getCreatedAt());
    assertNotNull(article.getUpdatedAt());
    log.info(article.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    log.info(article.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
}
​

```