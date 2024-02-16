## Github
## Github Pages
**Github의 정적 웹 사이트 호스팅 서비스**
- Public Repository의 HTML, CSS,JS 등을 게시해주는 서비스
- 복잡한 기능 없이 정보 공유용 사이트로 활용 (상업적 활용 금지)
- *.github.io 주소들은 Github Pages로 생성


**2가지 방법**
1. 평범한 Public Repository 이용
2. 자신의 Github 계정 이름을 딴 특수한 Repository 사용 => 이 방법으로 진행


**Repository 생성**
Github new repository
- Repository name : <Owner>.github.io
- Owner이름과 Repository name이 일치해야 한다.

---
## Jekyll
- Github의 설립자 중 하나가 Ruby를 이용해 만든 정적 웹사이트 생성기
- Github Pages는 Jekyll 을 가지고 만든 웹사이트를 호스팅 할 수 있다.

**Ruby 설치(windows)**
- RubyInstaller에서 최신 버전 설치
- git bash 사용
    - ruby --version 으로 ruby 설치 확인
    - gem install bundler
    - gem install github-pages
    - gem install jekyll


**Jekyll 테스트 해보기**
- 💡 .git을 제외한 파일을 삭제
- git bash
    - jekyll new .
    - bundle install
    - bundle exec jekyll serve =>  jekyll 실행 명령어


**Jekyll 테마 적용하기**
- https://github.com/artemsheludko/flexible-jekyll => zip파일로 다운로드
- .githuhb.io 폴더 내 파일 삭제 후 zip파일 폴더 복사, 붙여넣기
- bundle install
- bundle exec jekyll serve

---
**_config.yml 수정하기**
- 주소 설정
```yaml
# ...
baseurl: ""
url: "https://<github계정이름>.github.io"
# ...
```



- 블로그 소유주 정보 설정
```yaml
# Author Settings
author: 내 이름 # add your name
author-img: 내 프로필 사진 # add your photo
about-author: 내 소개 문구 # add description
social-twitter: # add your Twitter handle
social-facebook: # add your Facebook handle
social-github: 깃허브 계정 이름 # add your Github handle
social-linkedin: # add your Linkedin handle
social-email:  # add your Email address
```


**게시글 작성하기**
- 게시글은 _posts 경로에 작성
- 파일들은 설정 + Markdown
- title: 제목
- date: 시간정보, 파일 이름의 날짜와 일치해야 함, +0900
- img: 게시글 대표 이미지
- fig-cation: 이미지 설명
- tags: 해시태그, "," 로 구분해서 괄호[ ] 안에 작성 