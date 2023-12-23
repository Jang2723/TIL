## Github 기본 기능
- `git push` : 원격 저장소에 commit 업로드
- `git pull` : Github에 연결된 프로젝트 폴더에서 새로운 이력 가져오기


### Github에 Git 연결하기
1. `ssh-keygen` -> 키 생성
2. `cat ~/.ssh/id_rsa.pub` -> 생성된 키 확인
3. Settings의 SSH and GPG keys에 키 등록

### Github에서 생성한 Git Repository 내 컴퓨터로 가져오기
1. Github에서 Git Repository 만들기
2. 터미널에서 `git clone git@github.com:내 github 주소` 실행
3. 해당 폴더로 이동하여 파일 만들기, `git add`, `git commit` 진행 가능


### 내 컴퓨터에서 생성한 프로젝트 Github에 올리기
1. Github에 Repository 만들기 
2. 올리고 싶은 프로젝트 폴더로 이동 후 git bash 열기
3. 아래의 git 명령어 실행
```
git remote add origin git@gitnub.com: 내 github 주소/저장소.git
git push --set-upstream origin main
```

### Conflict
- Github에서 파일을 변경하고 동시에 같은 파일을 컴퓨터에서도 변경
- `git push` 시도 -> 실패!
- conflict 해결방벙
  1. 충돌이 발생한 파일을 수정
  2. `git add`와 `git commit`을 이용해 새로운 commit을 생성
  3. Conflict 해소하는 새로운 commit을 공유하기 위해 `git push`