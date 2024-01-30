## Authorization
- Authentication => 누구인지
- Authorization => **누구**가 무엇을 할 수 있는지 (= 권한)

```text
// Article의 기능
Article{
    CREATE
    READ
    UPDATE
    DELETE
}
```
> 💡Article(게시글) 접근 궈한에 따라 할 수 잇는 기능이 다름! 
> - `CREATE`는 접근하기 위해 로그인이 필요함   
> - `READ`는 접근하기 위해 로그인이 필요하지 않음
> - `UPDATE`/ `DELETE`의 접근 가능한 사람은
>   - 1. CREATE 한 사람
>   - 2. 관리자
----
1. [GrantedAuthority](1.GrantedAuthority.md)
2. [권한에 따른 접근 제어](2.접근제어.md)
3. [Article 응용](Article/3.Article응용.md)