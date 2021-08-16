## 3장 12요소 애플리케이션 설정

### 혼동스러운 설정

- 스프링에서서 설정을 말할 때는 일반적으로 빈을 어떻게 연결할지 컨테이너에게 알려주는 역할을 하는 스프링의 다양한 애플리케이션 컨텍스트 구현체에 대한 설정값을 의미한다.
- 12요소 애플리케이션 설정은 애플리케이션에서 정의한 설정을 의미한다.

### 스프링 프레임워크의 설정 지원

스프링은 PropertySourcesPlaceholderConfigurer 클래스가 도입된 이후 12 요소 애플리케이션 스타일의 설정을 지원한다.
- https://docs.spring.io/spring-framework/docs/5.3.10-SNAPSHOT/reference/html/core.html#beans-factory-placeholderconfigurer
- https://docs.spring.io/spring-framework/docs/5.3.10-SNAPSHOT/reference/html/core.html#beans-value-annotations

이를 통해 12요소 애플리케이션의 설정 스타일의 목표인 설정 정보를 외부화 하여 소스 코드를 새로 빌드하지 않아도 다른 설정 정보로 쉽게 교체 가능하다.
(12요소 방법론중 설정 및 코드 베이스를 적용할 수 있다.)

#### Environment 추상화와 @Value

Environment 추상화는 실행되는 애플리케이션과 그 애플리케이션이 실행되는 환경 사이에 참조를 통한 런타임 간접 지정을 제공한다

#### 프로파일

Environment에는 프로파일을 사용할 수 있다. 프로파일을 사용하면 빈을 그룹지어 사용할 수 있으므로, 실행 환경에 따라 달라지는 빈과 빈 그래프를 프로파일 변경만으로 쉽게 교체 적용할 수 있다
