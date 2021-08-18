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

### 스프링 부트 방식의 설정

스프링 부트는 별다른 설정 없이도 몇가지 지정된 위치에 정의된 설정 정보를 자동으로 읽어온다. (우선순위가 있으므로 주의 필요)

- https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config

클래스 패스에 SnakeYAML 라이브러리가 있으면 자동으로 YAML 파일에서도 properties 파일과 같은 방식으로 속성 정보를 가져올 수 있다.

### 스프링 클라우드 설정 서버로 중앙 집중형 설정 사용하기

지금까지 살펴본 설정정보 외부화 방식은 다음과 같은 문제점을 해결해 주진 않는다.

- 애플리케이션 설정 정보가 변경되면 애플리케이션을 재시작해야한다.
- 어떤 설정 정보가 수정되었는지 확인할 방법이 없고, 수정 내용을 이전 상태로 원상복구 할 방법이 없다. 즉 추적성이 없다.
- 설정이 여기저기 분산되어 있으므로 어디에서 어떤 설정 정보를 변경해야 하는지 파악하기 어렵다
- 손쉬운 암호화/복호화 방법이 없다.

#### 스프링 클라우드 서버 & 클라이언트

스프링 클라라우드 Config Server는 위와 같은 문제를 해결해준다.

- 스프링 클라우드 Config Server는 클라이언트와 설정 정보 저장소(추적성) 사이에 위차하여 다양한 기능을 제공할 수 있다. (암호화/복호화)
- 스프링 클라우드 Config Client는 Server와 통신하여 설정을 얻어올 수 있고, Refresh Scope를 통해 재시작 없이 변경사항을 반영할 수 있다.

##### 참고 자료

- Spring Cloud Context & Spring Cloud
  Common : https://docs.spring.io/spring-cloud-commons/docs/3.0.3/reference/html/#spring-cloud-context-application-context-services
- Spring Cloud Config : https://docs.spring.io/spring-cloud-config/docs/3.0.4/reference/html/#_quick_start

##### 스프링 클라우드 서버 & 클라이언트 예제코드

- https://github.com/songintae/spring-cloud-config

### 새로고침 가능한 설정

스프링 클라우드는 @RefreshScope 애노테이션으로 표현되는 리프래시 스코프를 통해 애플리케이션 재시작 없이 설정정보를 변경할 수 있다. 리프래시 스코프 안에 있는 빈은 RefreshScopeRefreshed
이벤트를 통지 받으면 *기존 빈을 완전히 폐지하고 다시 생성*한다.\
스프링에서는 RefreshScopeRefreshed 발행할 수 있는 다양한 Endpoint를 제공하는데 아래와 같은 종류가 있다

- spring actuator
- jconsole

Refresh 관련 공식 문서 : https://docs.spring.io/spring-cloud-commons/docs/3.0.3/reference/html/#refresh-scope \
Endpoint 관련 공식 문서 : https://docs.spring.io/spring-cloud-commons/docs/3.0.3/reference/html/#endpoints

#### 스프링 이벤트 버스

클라우드 네이티브 환경에서는 수십에서 수백대의 서버가 올라와 있는데, 위에서 설명한 방식으로 Refresh를 하는것은 현실적으로 어렵다. 스프링은 이러한 문제를 해결하기 위헤 스프링 클라우드 버스라는 기능을
제공한다. 모든 서비스는 스프링 클라우드 스트림이 장착된 버스를 통해 연결이 되고, 단 한번의 이벤트로 버스에 연결되있는 모든 서비스에 이벤트를 통지를 할 수 있다.

Spring Cloud Bus 공식 문서 : https://docs.spring.io/spring-cloud-bus/docs/3.0.3-SNAPSHOT/reference/html/
Cloud Config에서 Cloud Bus Notification 관련
문서 : https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_push_notifications_and_spring_cloud_bus

#### 예제 코드

- https://github.com/songintae/spring-cloud-config