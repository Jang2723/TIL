## MyBatis
### MyBatis Framework
- JDBC를 활용해 만들어진 Framework
  - 필요한 많은 과정을 추상화해서 복잡함 감소
- interface 메서드에 SQL 연결
  - 메서드 호출 시, SQL 실행
---
### Mybatis 설정
- `application.yaml`
```yaml
# DB 관련 설정
spring:
  datasource:
    # 예전에 JDBC Connection 만들때 제공했던 JDBC URL
    url: jdbc:sqlite:db.sqlite
    # 어떤 데이터베이스를 쓰느냐에 따라 어떤 Driver를 사용해야 하는지가 달라진다.
    driver-class-name: org.sqlite.JDBC
    # username: sa
    # password: password
```
- `spring.datasource.url` : 데이터베이스에 접속하기 위한 URL 설정
- `spring.datasource.driver-class-name` : 데이터베이스를 사용할 때 사용할 JDBC 드리아버 설정, 사용하는 RDBMS에 따라 다름
- `spring.datasource.username / password` : 데이터베이스의 접속 정보를 작성하는 곳(SQLite는 사용x)

```yaml
# MyBatis 관련 설정
mybatis:
  mapper-locations: "classpath:/mybatis/mappers/*.xml"
  type-aliases-package: "com.example.crud.model"
  configuration:
    map-underscore-to-camel-case: true
```
- `mybatis.mapper-locations` : SQL이 정의된 mapper.xml 파일의 위치 경로
- `mybatis.type-aliases-package=com.example.mybatis.model` : 위에서 언급한 XML 파일에서 사용할 Java 클래스의 패키지
- `mybatis.configuration.map-underscore-to-camel-case=true` : `snake_cake`와 `camelCase`간의 자동 변환
> 💡 `application.yaml` 설정이 계층 구조로 이루어져 있어 가독성이 뛰어남!
---
### Annotation으로 SQL 작성
`Lombok` `@Data` 활용
- Getter, Setter
- RequiredArgsConstructor
- ToString
- EqualsAndHashCode
- Value
---
- `Student.java`
```java
import lombok.Data;

@Data
public class Student {
    // 데이터베이스의 PK
    private Long id;
    private String name;
    private String email;
}
```
- MyBatis는 기본적으로 메서드의 SQL 구문을 매핑해서 사용
- MyBatis가 제공하는 어노테이션을 interface에 추가하면 간단하게 매핑 가능
- `StudentMapper.java` 
```java
// interface
@Mapper
public interface StudentMapper{
    @Select("SELECT * FROM student")
    List<Student> selectStudentAll();
}
```
- `@Mapper` : MyBatis를 사용하면서 메서드와 SQL쿼리를 연결하는 가장 기본적인 방법
> 💡 `selectStudentAll()` 호출 시 `SELECT * FROM student` 실행   
> 💡 `@Select`, `@Insert`,`@Update`,`@Delete` 등 작성 가능
----
- `StudentDao.java`
- Data Access Object(DAO) : 데이터베이스 접근에 대한 관심사 분리
```java
// 데이터 통신을 담당하는 클래스임을
// Spring에 알려줌
@Repository
public class StudentDao {
    // MyBatis와 데이터베이스를 연결해주는 객체
    private final SqlSessionFactory sessionFactory;
    // Spring Boot안에 만들어진 SqlSessionFactory Bean이 자동으로 주입된다.
    public StudentDao(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    // 데이터베이스에서 학생 데이터를 전부 불러오는 메서드
    public List<Student> readStudentsAll() {
        // SqlSession은 MyBatis와 데이터베이스가 연결되었다는 것을 상징하는 객체
        try (SqlSession session = sessionFactory.openSession()){
            // Mapper 인터페이스를 가져온다.
            StudentMapperA studentMapper = session.getMapper(StudentMapperA.class);
            return studentMapper.selectStudentAll();
        }
    }
}
```
- `@Repository` : Spring Bean으로 등록
- `SqlSessionFactory` : MyBatis가 제공하는 추상화된 JDBC 사용의 기초, 데이터베이스와의 연결을 담당하는 객체
- `SqlSession` : 데이터베이스와의 세션을 나타내는 객체, Mapper interface 제공


1. Dao는 객체를 만들고,Bean 객체로 등록하여 데이터베이스와의 연결을 담당하는 `SqlSessionFactory` 객체를 주입받음
2. 주입받은 `SqlSessionFactory`의 `openSession()` 메서드를 활용해, 데이터베이스에 연결되었음을 뜻하는 `SqlSession` 객체를 받음
3. `SqlSession` 객체를 통해, MyBatis에 등록된 `@Mapper` 인스턴스를 반환받음
4. `@Mapper` 인스턴스의 메서드를 호출하면, 등록해둔 SQL 실행
----
### SQL에 인자 전달
- `#{param}` : 전달 받은 param 인자의 값을 활용해 SQL 해당 부분을 대치
- `${param}` : 전달 받은 param 인자의 값을 그대로 사용해 SQL에 해당 부분을 대치 => **SQL Injection 위헙**
- Mapper Annodation CRUD (Insert, Select, Update, Delete)
1. `@Insert`
```java
@Insert("INSERT INTO student(name, age, phone, email)\n" +
        "VALUES(#{name}, #{email})")
void insertStudent(Student student);
```

2. `@Select`
```java
@Insert("INSERT INTO student(name, age, phone, email)\n" +
        "VALUES(#{name},  #{email})")
void insertStudent(Student student);
```

3. `@Update`
```java
@Update("UPDATE student SET " +
        "name = #{name}," +
        "email = #{email}" +
        "WHERE id = #{id}")
void updateStudent(Student student);
```

4. `@Delete`
```java
@Delete("DELETE FROM student " +
        "WHERE id = #{id}")
void deleteStudent(Long id);
```

5. `@Select - Optional`
```java
@Select("SELECT * FROM student WHERE id = #{id}")
Optional<Student> selectStudentOptional(Long id);
```
----
### XML로 SQL 작성
- MyBatis는 SQL을 다른 XML파일에 작성한 뒤 해당 XML에 작성한 SQL을 Java interface에 매핑
- `@Select` 같은 어노테이션 사용 x
- `interface` 정의
```java
public interface StudentMapperX {}
```
- `StudentMapper.xml` 추가
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```
- 이 XML파일이 MyBatis에서 사용하는 Mapper 형식이라는 의미
```xml
<mapper namespace="com.example.mybatis.mapper.StudentMapperX">

</mapper>
```
- `namespcae` : 연결하고자 하는 interface
- `"com.example.mybatis.mapper.StudentMapperX"` : 이 XML과 연결할 실제 mapper interface

```xml
    <select id="selectStudent" parameterType="Long" resultType="Student">
        SELECT * FROM student WHERE id = #{id}
    </select>
    <select id="selectStudentAll" resultType="Student">
        SELECT * FROM student;
    </select>
```
- `<select>` 요소의 `id`와 동일한 이름의 메서드를 `StudentMapperX.java`에 추가
- `parameterType` : SQL에 전달할 인자 타입
- `resultType` : SQL 결과로 받을 타입
```java
public interface StudentMapperX {
		Student selectStudent(Long id);
		List<Student> selectStudentAll();
}
```
> 💡 단점 : XML 파일로 SQL을 분리하게 되면 XML을 별도로 관리하고 , Java interface와 잘 매핑되었는지 지속적으로 확인해야 함    
> 💡 장점 : 크고 복잡한 SQL을 관리