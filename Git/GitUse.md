## Git 기본 기능

### 사전 작업
Git 설치 후 자신이 누군지를 설정하는 작업
```
git config --global user.email "EMAIL"
git config --global user.name "USERNAME"
```
- git : Git을 사용하는 명령어
- config : Git 설정 명령어
- --global : 프로젝트와 상관없이 이 컴퓨터에 대하여
- user.email : Git 사용자 email
- user.name : Git 사용자 이름

### Git 기본 기능
- `git init` : 현재 폴더를 Git으로 관리 


- `git status` : 현재 프로젝트의 상태 보기   


- `git add file_name` : 어떤 파일을 작업 이력으로 기록할 준비
  - git add 하기 전에는 파일이 이름이 붉은 색으로 표시
  - git add 한 후에는 파일의 이름이 녹색으로 표시
  - `git add --all` or `git add .` : 모든 파일 추가 


- `git commit -m "file_name"` : add 된 파일들을 바탕으로 새로운 변경사항(작업)을 기록
  - -m : commit 메시지 입력
  - "" : 메시지 내용


- `git log` : 작업 이력(commit) 확인하기
  - `git log --oneline` : git log를 간단하게 보여줌


### Git 되돌리기
- commit 되지 않은 작업
  - git add 하기 전 
    - `git restore <파일 이름>`
  - git add 진행 이후
    - `git resotre --staged <파일 이름>`


- commit 되돌리기
  -  `git reset 목표상태`
