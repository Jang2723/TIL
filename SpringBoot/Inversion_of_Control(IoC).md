## Spring Boot Start
### Ioc Container
- 제어 반전을 구현하기 위해 Spring Framework에서 사용하는 것
> 💡 Container : 인스턴스의 생명 주기를 관리 및 생성된 인스턴스들에게 추가적인 기능 제공

### Inversion of Control(제어 반전)
- 클래스를 만드는 주체는 **개발자** 
- 그 클래스 인스턴스가 언제 만들어지는지 결정하는 주체는 **Spring Boot**

- Spring Boot는 내부적으로 요청을 받는 부분이 만들어져 있는 상태이고, 
- Spring Boot를 사용하는 개발자는 어떤 요청을 받았을 때 어떤 행동을 할지만 정해주면 되는 형태
- => 제어하는 주체가 역전이 된 상황!


- Spring Framework는 이 제어 반전을 구현하기 위해 그 중심에 IoC Container를 가지고 있고,
- 저희가 정의해둔 클래스를, 필요한 상황에 맞게 객체로 생성하고, 관리하며, 
- 또 그 객체를 필요로 하는 다른 객체가 있을 때 이미 있는 객체를 다시 활용할 수 있도록 만들어져 있습니다.
> 💡 IoC Container는 Spring Container, 또는 ApplicationContext로 지칭되기도 합니다.


