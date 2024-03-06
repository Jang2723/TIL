## Querydsl
Spring Data JPA를 사용하면 JPA의 기능을 강력한 `JpaRepository`를 이용해 활용할 수 있으며, JPQL을 이용해 수동으로 쿼리를 작성하는 등 다양한 기능 활용 가능
 
- JPQL : 문자열을 사용해 개발자가 직접 쿼리를 작성
  - 단점 
  1. 조금만 길어져도 직접 작성하기 어려움
  2. 동적 쿼리를 만들기 어려움
  3. 실행 전에 정상 작동할지 알기 어려움


=> 그래서 JPQL의 보완적인 역할을 하는 [Querydsl](https://querydsl.com/) 프레임워크를 사용

- [1. Querydsl](1.Querydsl.md)
- [2. Querydsl 사용해보기](2.Querydsl-use.md)
- [3. 기본적인 데이터 조회](3.기본적인_데이터조회.md)