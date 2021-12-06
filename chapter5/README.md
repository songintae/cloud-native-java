## 5장 애플리케이션 마이그레이션

### 계약

클라우드 파운드리는 애플리케이션 배포나 관리에 관한운영 측면의 이슈를 줄이거나 또는 일관성을 유지해줌으로써 작업 속도를 개선하는 것을 목표로 한다. \
이러한 목표를 달성하기 위해서는 **애플리케이션이 올라가는 플랫폼과 애플리케이션 사이에는 내부적으로 지켜야하는 계약이 있는데**, 이 계약을 통해 플랫폼이 애플리케이션을 정상적으로 실행하고 운영한다는것을 보장할 수
있다. \

이번장에서는 기존 애플리케이션을 클라우드 네이티브 환경에 마이그레이션 하기위한 애플리케이션 포크리프팅에 대해 알아본다.

### 애플리케이션 환경 마이그레이션

#### 바로 가져다 쓰는 빌드팩

클라우드 파운드리는 애플리케이션이 실행되는 환경을 아주 명확하게 가정한다. 이런 가정은 허로쿠(Heroku)에서 도입한 빌드팩(BuildPack)에 담겨있기도 하다. 빋드팩에는 jar, war, 레일즈 애플리케이션,
노드제이에스 애플리케이션 등이 클라우드 파운드리에서 컨테이너로 만들어지는데 필요한 정보가 담겨있다. 이를통해 클라우드 파운드리는 애플리케이션에 종류와 관계없이 리눅스 컨테이너만 신경쓰고 관리할 수 있게된다.

- [Cloud Native BuildPack](https://buildpacks.io)

스프링에서도 클라우드 네이티브 빌드팩에서 정의한 계약에 따라 OCI(Open Container Image)를 생성하는 Bulider Plugin을 제공한다

- [Packaging OCI Images](https://docs.spring.io/spring-boot/docs/2.5.0/gradle-plugin/reference/htmlsingle/#build-image)

#### 커스텀 빌드팩

빌드팩은 원하는 대로 설정할 수 있는 유연성을 유지하면서도 합리적인 기본값을 제공한다. 하지만 시스템 빌드팩이 요구사항에 마짖 않는다면, 다양한 커스텀 빌드팩을 사용할 수 있다.

#### 컨테이너화된 애플리케이션

자바EE와 같은 애플리케이션은 환경에 따라 다른 환경으로 마이그레이션 하기 어려운 경향이 있다. 이를 해결하기 위해 클라우드 파운드리는 Dokcer 등을 통해 컨테이너화(OCI)된 애플리케이션을 지원한다.

### 애플리케이션을 클라우드로 옮기기 위한 가벼운 리팩토링

이번 단원에서는 애플리케이션의 마이그레이션에 필요한 가벼운 조정을 최소한의 위험으로 수행하는 방법을 다룬다.

#### 지원 서비스 연동

지원 서비스란 데이터베이스, 메시지 큐, 이메일 서비스 등 애플리케이션이 사용하는 외부 서비스를 말한다.\
12요소 애플리케이션 설정에서 이야기했듯이, 이런 지원서비스 자원 교체하는 일은 그저 의존관계를 다시 이어주는 작업에 불과할정도로 간단하게 다룰 수 있어야한다.

#### 스프링을 이용한 서비스 짝 맞춤

이번 절에서는 애플리케이션을 가벼운 컨테이너나 클라우드로 옮길 때 마주치는 난관을 해결하는 방법을 살펴본다.

##### 원격 프로시저 호출

대부분의 클라우드 서비스는 호환성이 좋은 HTTP를 우선으로 처리한다. 따라서 기존에 원격 프로시저를 호출하던 기능들은 스프링의 HTTP 인보커 서비스와 같은 기술을 사용해 HTTP를 통한 원격프로시저를 호출하도록
변경해주는게 좋다.

- [Spring HTTP Invoker (Deprecated)](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#remoting-httpinvoker)

##### 스프링 세션으로 HTTP 세션 다루기

전통적인 애플리케이션 서버에서는 세션 복제가 단일 클러스터 내에서의 브로드캐스팅 네트워크 기능이었다. 아쉽지만 이런 전통적인 세션 복제는 성능이나 견고함, 이식성이 썩 좋지 않다.\
스프링 세션은 세션 동기화를 위해 SPI(Service Provider Interface)에 의존하는 서블릿 HTTP 세션을 쉽게 대체할 수 있다.

- [SPI 란?](https://en.wikipedia.org/wiki/Service_provider_interface)
- [Http Session Integration](https://docs.spring.io/spring-session/docs/2.5.3/reference/html5/#httpsession)

##### 자바 메시지 서비스

스프링에서는 JMS API를 간단하게 다룰 수 있는 Integration을 제공한다.

- [JMS](https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_%EB%A9%94%EC%8B%9C%EC%A7%80_%EC%84%9C%EB%B9%84%EC%8A%A4)
- [Spring JMS Integration](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#jms)

##### X/Open XA 프로토콜과 JTA를 사용하는 분산 트랜잭션

X/Open XA 프로토콜은 분산 트랜잭션 처리를 위해 오픈그룹에서 재정의한 명세를 말한다. XA는 2단계 커밋을 이용해서 분산된 모든 자원이 하나의 트랜잭션 안에서 ACID하게 처리될 수 있게 한다. \
스프링에서는 JTA를 사용하여 분산 트랜잭션을 제공한다.

- [X/Open XA 프로토콜](https://ko.wikipedia.org/wiki/X/Open_XA)
- [Distributed Transactions with JTA](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-jta.html)
