## Markup Language
- 문서가 표현하는 데이터가 어떤 구조를 가지고 있는지를 명시하기 위한 언어 및 기호의 집합
- 대표적으로 html, markdown이 있다.
- Programming Language랑 많이 다르다   
  - 문서의 구조를 표현   
  - 이 문서를 다시 해석하는 기계(프로그램)이 실제 모습을 조정    
  - 표현의 자동화를 위한 언어 

## Markdown
- 마크업 언어(Markup Language)의 일종
- 특징
  - 문법이 쉽고 간결하다.
  - 관리가 쉽다. 
  - 별도의 도구 없이 쉽게 작성이 가능하다. 
  - 다양한 플랫폼(gitlab, github 등)에서 사용한다.

## Markup 기본 문법 정리

### 문단
- enter 한번    

`문단이다. 
엔터 한번으로는 줄이 바뀌지 않는다.`

- enter 두번    
```
문단이다.

엔터 두번이 되어야 줄이 바뀌고 다음 문단으로 인식한다.
```

### 줄바꿈(Line Breaks)
새로운 문단이 아닌, 문단 내부에 엔터를 추가하고 싶다면  
공백 두개와 함게 엔터를 해주면 줄이 바뀐다.

### Headings - 문단 제목
- 제목, 부제 등을 표현할 때는 `#`을 사용
- `#`은 6개까지 사용할 수 있으며 많을 수록 더 작은 범위의 문단을 의미

### Emphasis - 글 강조
- `*`: 앞뒤로 사용시 해당 글귀 기울어짐 : *기울어짐*
- `**`: 앞뒤로 사용시 해당 글귀 굵어짐 : **굵어짐**
- `***`: 상기 두 가지를 모두 적용 가능 : ***모두 적용***

### Block Quotes - 인용문
- 문단의 시작에 `>`를 작성

ex)
> A quotation is the repetition of a sentence, phrase, or passage from speech or text that someone has said or written.

### Lists - 목록
- `1.`,`2.`,`3.`... 등 숫자를 앞에 붙이면 순서를 가진 리스트 작성이 가능 
- `-`, `*`, `+` 등을 사용하면 순서 표현이 없는 리스트 작성 가능
  - 섞어서 사용해도 목록이 만들어지나 권장하지는 않음
- 목록 하위에 `Tap` 또는 들여쓰기 4칸 사용 후 작성시 하위 목록으로 작성 가능

ex)
1. 일어나기
2. 세수하기
3. 컴퓨터 켜기
    - `-` 사용
    + `+` 사용
    * `*` 사용
    * 들여쓰기 4칸 사용 

### Code - 코드
- 코드를 표현하고 싶을 때에는 `` ` ``(백틱)으로 감싸서 표현이 가능
- 작성하고 싶은 코드에 백틱이 포함되어 있다면 두 개의 백틱으로 감싸서 표현 가능
- 여러 줄의 코드를 표현하고 싶을 때에는 백틱 세 개를 사용하는 확장 문법 사용 가능

ex)
- 한 줄 코드   
`System.out.println("Hello World")`
- 여러 줄 코드
```
public class Main {
  public static void main(String[] args) {
    System.out.println("Hello Java!");
  }
}
```
- 해석 서비스에 따라 코드에 사용하는 언어를 알려주면 해당 언어의 문법에 맞게 색 변화 적용 가능

ex)
```java
public class Main {
  public static void main(String[] args) {
    System.out.println("Hello Java!");
  }
}
```

### Horizontal Rules - 가로줄
- 가로 구분선 사용시 `---` 사용    

ex)

---

### Links - 링크
- `<>` 안에 링크 작성시 연결 가능
- `[]()` 대괄호 안에 텍스트, 소괄호 안에 링크 작성시 텍스트 클릭으로 링크 접속 가능

ex)   
[네이버](https://www.naver.com)

### Images - 이미지
- `![]()` 대괄호에 이미지 이름, 소괄호에 이미지 위치 삽입시 이미지 추가 가능  
  **웹 상의 이미지도 추가 가능**

ex)   
- 폴더 내의 이미지 추가   
![스누피](스누피.png)


- 웹 상의 이미지 추가   
![Sping Boot](https://upload.wikimedia.org/wikipedia/commons/4/44/Spring_Framework_Logo_2018.svg)
