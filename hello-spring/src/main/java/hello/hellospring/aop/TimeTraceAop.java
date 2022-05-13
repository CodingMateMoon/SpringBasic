package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
/* @Component
Component 스캔을 사용할 수도 있지만 Aop같은 경우는 @Configuration SpringConfig 설정 파일에서 스프링 빈으로 등록해서 사용하는 것을 선호합니다.
평범한 service repository는 정형화돼서 만들 수 있지만 AOP의 경우 특별하게 쓸 수 있음을 보여줄 수 있습니다.
 */
public class TimeTraceAop {

    // 공식 문서 등 매뉴얼을 참조합니다.
    /*
    원하는 곳에 공통관심사항을 적용하는 것. 타겟팅을 할 수 있습니다.
    execution(* hello.hellospring..*(..)) -> 해당 hello.hellospring의 하위패키지에 모두 AOP를 적용하라는 표현식입니다.
    * */
    //@Around("execution(* hello.hellospring..*(..))")
    @Around("execution(* hello.hellospring.service..*(..))") // service 하위 패키지에만 AOP를 적용합니다.
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable {
        long start = System.currentTimeMillis();
        /* joinPoint.getArgs(), getTarget()
        메소드 호출이 될 때마다 joinPoint에 argument가 뭔지 어느 타겟에 호출하는지 지금 내가 누군지 등 원하는 것을 조작할 수 있습니다.
        메소드를 호출할 때마다 인터셉터(요청을 가로채는 것)가 걸립니다.
         */
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
/*
AOP를 적용하고  AOP를 어디에 적용할지 지정하면 서비스가 지정됩니다[@Around("execution(*hello.hellospring.service..*(..))") ]. \
스프링은 AOP가 있으면 가짜 멤버 서비스, 즉 프록시를 만들어냅니다.
스프링 컨테이너는 컨테이너에 스프링 빈을 등록할 때 진짜 스프링 빈이 아닌 가짜 스프링 빈을 앞에 세워둡니다.
가짜 스프링 빈이 끝나고 joinPoint.proceed()를 하면 그때 진짜 스프링빈을 호출합니다.
멤버컨트롤러가 호출하는 것은 진짜 멤버서비스가 아니라 프록시라는 기술로 발생하는 가짜 멤버서비스입니다.
멤버컨트롤러에서 멤버서비스가 injection 주입되는데 프록시의 경우 멤버서비스를 복제해서 코드를 조작합니다.
스프링 컨테이너가 AOP가 적용이 되면 "너 멤버서비스에 AOP가 적용이 되야하네"하며 프록시 가짜가 등록되고
AOP가 실행되며 joinPoint.proceed()가 호출이 되면 (핵심 관심사) 진짜 스프링빈 MemberService를 호출합니다.
EnhancerBySpringCGLIB : CG 라이브러리. 멤버 서비스를 가지고 복제해서 코드를 조작하는 기술.

AOP 기술중에는 스프링 컨테이너에서 스프링 빈을 관리하면 가짜를 만들어서 의존성 주입(dependency injection. DI)를 해주면 됩니다.
DI를 안하면 직접 멤버컨트롤러에서 new로 멤버서비스를 만들고 서비스의 메소드를 호출해야하는데 AOP 방식의 가짜 멤버서비스 프록시를 만드는 과정이 불가능해집니다.
멤버컨트롤러는 서비스가 뭔지 모르겠고 받아서 쓸게라고 하는데 이때 프록시가 들어와서 AOP를 가능하게 해줍니다. 스프링은 프록시 방식의 AOP입니다.
자바에서 컴파일타임에 코드를 생성후 자바코드를 위아래로 박아서 넣어주는 경우도 있습니다.
* */

/* 다음으로
스프링컨테이너가 왜 나왔고 왜 쓰는가. AOP처럼 쭉 하니까 이래서 스프링에서 DI가 필요하고 이래서 스프링 프레임워크가 만들어졌구나라는것을 이해합니다.
스프링과 관련된 핵심기술 DI AOP를 깊이있게 설명합니다. 스프링 웹 MVC 서블릿 jsp 공부하면서 넘어왔지만 추상화가 잘되어있어서 스프링 부트로 웹 mvc 개발하는것을 보여주면서 설명합니다.
기본적으로 웹 mvc에 대해 깊이있게 알려면 서블릿을 필수적으로 알아야합니다. 스프링이 어떤 패턴으로 동작하는지 서블릿 기반의 기술들. 필터 인터셉터 예외처리 등 실무 트렌드에 맞게 설명합니다.
스프링 디비 접근 기술의 경우 개발에서 진짜 중요한 것은 데이터베이스를 어떻게 접근하고 관리하는것입니다.
스프링 관련 트랜잭션 및 예외처리. 스프링이 예외를 잘 추상화한 내용, 데이터베이스 커넥션, 데이터 베이스 커넥션 풀을 스프링에서는 어떻게 설정하고 실무에서는 어떻게 돌아가는지 커넥션 풀링,
Jdbc템플릿이나 jpa 관련 기술들에 대해 학습합니다. 스프링 부트는 방향에 대해 고민중인데 스프링부트는 스프링을 편리하게 쓸 수 있도록 도와주는것입니다.
고민하는 방향성은 부트에 대한 기본적인 메커니즘. 스프링 부트 매뉴얼 중에서 실무에서 꼭 필요한것 모니터링, 프로파일,
configuration 스프링을 띄울 때 로컬에서는 어떻게하고 운영에서는 어떻게하는지, 실무에서는 어떻게하는지, 실제 실무에서는 스프링부트를 어떻게 쓰는지에 대한 활용방안
예를 들어 스프링 부트에서 모니터링을 하는 기술이 있는데 실제로 어떤식으로 모니터링을 하는지 등 실무에 가깝게 설명합니다.
* */