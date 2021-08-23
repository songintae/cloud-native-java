## 4장 테스트

클라우드 네이티브 애플리케이션은 개별 컴포넌트에서 전체 시스템까지 모든 단계에서 빠른 피드백에 최적화되어 있다.
**테스트**는 빠른 피드백 순환을 가능하게 하는 가장 중요한 수단이다. \
애플리케이션이 점점 분산화 되면서 테스트를 효율적으로 작성하는 전략도 많이 바뀐다. 통합테스트는 소프트웨어 개발 과정에서 통합되어 동작하는 분리된 여러 모듈이나 컴포넌트가 예상대로 동작하는지 보장해주는 테스트 케이스
실행을 자동화할 수 있는 표준 방법이다.

### 테스트의 구성

일반적으로 테스트 관련 코드는 src/test 하위에서 관리를 한다. 하지만 많은 조직에서는 통합 테스트 코드를 지속적으로 손쉽게 실행할 수 있도록 별도의 폴더 아래로 두기도 한다. 어떤 접근 방식을 취하든, **
모든 테스트를 자동화해야한다.**

### 스프링 부트 애플리케이션 테스트

스프링 부트 애플리케이션의 테스트는 크게 단위 테스트와 통합 테스트로 구분할 수 있다. 실행중에 스프링 애플리케이션 컨텍스트에 접근하는 모든 테스트는 통합 테스트라고 할 수 있다.\
스프링 통합테스트는 JUnit 프레임워크와 같이 동작하고 기본적으로 JUnit5를 지원한다.

- 스프링 테스트 공식
  문서 :  https://docs.spring.io/spring-framework/docs/5.3.10-SNAPSHOT/reference/html/testing.html#testing-introduction
- SpringBoot Test Feature
  문서 : https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications
- JUnit5 문서 : https://junit.org/junit5/docs/current/user-guide/#overview