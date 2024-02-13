## File Handling
-  JSON은 텍스트 기반 데이터를 옮기는데 매우 효율적이 수단

### 정적 파일(Static)
- 사용자에게 변환 없이 전달되는 파일
  - CSS, 이미지, 영상, 몇몇 HTML 등
- Spring Boot `resource/static`에 파일을 저장하면 정적 파일 전달 가능(기본 설정)
- `application.yaml` 설정을 바꾸면 요청 경로 변경 가능
  - `spring.mvc.static-path-pattern: /static/**` 
```yaml
spring:
  mvc:
    # 어떤 경로에 대한 요청의 응답으로
    # 정적 파일 응답을 할지 결정하는 설정
    static-path-pattern: /static/**
  web:
    resources:
      # 어떤 폴더의 파일을 정적 응답으로 전달할지 설정
      static-locations: file:media/,classpath:/static
```
--- 
## Multipart
> 💡 [실습](https://github.com/Jang2723/Spring-Multipart)
### form
- HTML에서 JS 없이 데이터를 전송(HTTP 요청)할 때는 => `form` 요소 사용
- 내부에 `input`요소를 이용해 전달할 데이터 정의
- `input type="submit"`을 이용해 `form`요소 내부의 데이터 수합
- `action` 속성에 정의된 URL로 요청을 보냄,보낼때 데이터 인코딩 방식을 결정해야 함
- `enctype` 속성으로 데이터 인코딩 방식 정의 가능
  - `application/x-www-form-urlencoded` (기본값) : `input` 데이터를 모아 하나의 문자열로 표현해 전송
  - `multipart/form-data` : 각각의 `input` 데이터를 개별적으로 인코딩해, 여러 부분(multi part) 로 나눠서 전송
  - 파일 같이 별도의 인코딩이 필요한 경우 `multipart/form-data`의 방식을 활용
```html
<form enctype="multipart/form-data">
  <input type="text" name="name">
  <input type="file" name="photo">
  <input type="submit">
</form>
```
> ✔ 참고) `input type="image"`는 이미지 업로드용이 아닌, 이미지를 이용해 제출버튼을 표현하고 싶을 때 사용하는 형식

---
### Spring에서 Multipart File 받기
- `@RequestParam`으로 `MultipartFile` 인자를 받을 수 있음
  - 이때 `@RequestMapping`의 `consumes`설정이 필요
```java
@PostMapping(
        value = "/multipart", 
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ResponseDto multipart(
        @RequestParam("name") String name,
        // 받아주는 자료형을 MultipartFile
        @RequestParam("photo") MultipartFile multipartFile
) throws IOException {
```
- 이떄 `RequestParam`에 전달하는 인자가 `input`요소의 `name`에 해당하는 값
- 전달받은 `MultipartFile`은 `transferTo()` 메서드를 이용해 파일 평태로 저장
```java
// 저장할 경로를 생성한다
Files.createDirectories(Path.of("media"));
// 저장할 파일이름을 경로를 포함해 지정한다.
Path path = Path.of("media/filename.png");
// 저장한다.
multipartFile.transferTo(path);
```
- 저장 이전에 데이터를 확인하여 작업을 하고 싶다면 `getBytes()`를 통해 `byte[]`의 형으로 변환하여 중간에 작업 추가 가능
```java
// 저장할 파일 이름
File file = new File("./media/filename.png");
// 파일에 저장하기 위한 OutputStream
try (OutputStream outputStream = new FileOutputStream(file)){
    // byte[] 데이터를 받는다.
    byte[] fileBytes = multipartFile.getBytes();
    // 여기에서 추가작업
    
    // OutputStream에 MultipartFile의 byte[]를 저장한다.
    outputStream.write(fileBytes);
}
```
---
### 업로드 된 데이터 돌려주기
- 사용자가 업로드한 데이터는 저장한 이후 되돌려 줄 떄 정적 파일의 형태로 응답
- 업로드 한 파일을 전부 한 폴더에 저장하고
- 해당 경로에 존재하는 파일을 정적 파일의 형태로 전송
- `application.yaml` 수정
```yaml
spring:
  web:
    resources:
      static-locations: file:media/,classpath:/static
```
- `,` 로 경로 두가지 구분 
- `file:media/` : 현재 실행 중인 경로의 `media`라는 폴더
- `classpath: /static` : 빌드된 어플리케이션의 클래스패스의 `/static` 경로 ( 즉, `resource/static` )
- 전에 설정한 `spring.mvc.static-path-pattern: /static/**`과 합쳐지면 사용자가 `/static/<정적파일 경로>`로 요청을 보내게 될때
  - 작성한 순서대로 `media` 폴더 내부, `static`폴더 내부에서 `<정적 파일 경로>` 파일을 응답 받음

> 💡 [사용자 프로필 이미지 업로드 기능](UserAvatar.md)