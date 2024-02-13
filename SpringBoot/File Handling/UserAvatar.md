## File Handling
### User 프로필 이미지 저장 기능
- 회원 가입을 진행, 이때는 프로필 사진이 없는 상태
- 프로필 사진을 User가 업데이트, 프로필 이미지는 Multipart 형태로 전송될 것
- 특정 경로(정적 파일을 관리하는 경로)에 프로필 사진을 저장, 특정 URL로 접근할 시 확인 가능
- User 테이블에는 `String`으로 프로필 사진을 추가
```java
// User Entity
package com.example.contents.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    @Setter
    private String phone;
    @Setter
    private String bio;
    @Setter
    private String avatar; // 이미지 경로

    public User(String username, String email, String phone, String bio) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
    }
}
```
- 사용자의 계정 정보를 나타내는 `User`
- 프로필 사진을 업로드 한다면 `media`경로에 저장
- 해당 파일에 접근할 수 있는 URL을 `User.avatar`에 업데이트
> 💡 `multipart/form-data` 요청의 경우 용량이 큰 편    
> => 정보 업데이트를 JSON요청으로 받는 URL, 프로필 이미지 업로드 하기 위한 URL을 따로 만드는 것을 고려

### User Service 비즈니스 로직 구성
```java
// User Service
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    // CREATE USER
    // 회원가입
    public UserDto create(UserDto dto){
        // 사용자 생성 전 계정 이름 겹침 확인 후
        // 확인 했을때 겹칠 경우 400
        /*if (repository.findByUsername(dto.getUsername()).isPresent())*/
        if (repository.existsByUsername(dto.getUsername()))
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"duplicate username");
            // throw new IllegalArgumentException("duplicate username");
            throw new UsernameExistsException();

        User newUser = new User(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getBio()
        );
        newUser = repository.save(newUser);
        return UserDto.fromEntity(newUser);
    }


    // READ USER BY USERNAME
    // 회원 정보 조회
    public UserDto readUserByUsername(String username){
        Optional<User> optionalUser
                =  repository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return UserDto.fromEntity(optionalUser.get());
/*        return repository.findByUsername(username)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));*/
    }

    // UPDATE USER AVATAR
    // 회원 프로필 아이콘 업데이트
    public UserDto updateUserAvatar(Long id, MultipartFile image){
        // 1. 유저의 존재 확인
        Optional<User> optionalUser
                = repository.findById(id);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 2. 파일을 어디에 업로드 할건지 결정
        // media/{id}/profile.{확장자}  ->> 관리 기획
        // 2-1. (없다면) 폴더를 만들어야 한다.
        String profileDir = String.format("media/%d/", id);
        log.info(profileDir);
        // 주어진 Path를 기준으로, 없는 모든 디렉토리를 생성하는 메서드
        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            // 폴더를 만드는데 실패하면 기록하고 사용자에게 알림
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 2-2. 실제 파일 이름을 경로와 확장자를 포함하여 만들기
        String originalFilename = image.getOriginalFilename();
        // whale.png -> { "whale", "png" }
        // "blue.whale.png" -> {"blue", "whale","png"}
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String profileFilename = "profile." + extension;
        log.info(profileFilename);

        String profilePath = profileDir + profileFilename;
        log.info(profilePath);

        // 3. 실제로 해당 위치에 파일을 저장
        try{
            image.transferTo(Path.of(profilePath));
        }catch (IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 4. User에 아바타 위치를 저장
        // http://localhost:8080/static/{id}/profile.{확장자}
        String requestPath = String.format("/static/%d/%s", id, profileFilename);
        log.info(requestPath);
        User target = optionalUser.get();
        target.setAvatar(requestPath);

        // 5. 응답하기
        return UserDto.fromEntity(repository.save(target));

    }
}
```
### User Controller 구성
```java

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto create(
            @RequestBody
            UserDto dto
    ) {
        return service.create(dto);
    }

    @GetMapping("/{username}")
    public UserDto read(
            @PathVariable("username")
            String username
    ) {
        return service.readUserByUsername(username);
    }

    // 사용자 정보 수정
    @PutMapping("/{userId}/avatar")
    public UserDto avatar(
            @PathVariable("userId")
            Long userId,
            @RequestParam("image")
            MultipartFile imageFile
    ) {
        return service.updateUserAvatar(userId, imageFile);
    }

    // 컨트롤러 단위에서 예외처리를 하고싶은 경우
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 없으면 200 ok로 나옴
    public ErrorDto handleIllegalArgument(
            final IllegalArgumentException exception){
        log.warn(exception.getMessage());
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return  dto;
    }
}
```


### User Repository 구성
```java

import com.example.contents.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // User findByUsernameEquals(String username); // 위 아래 둘다 기능은 같지만 null 값 반환의 차이
    boolean existsByUsername(String username);

}
```

----
### Multipart 요청 크기 제한
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
```