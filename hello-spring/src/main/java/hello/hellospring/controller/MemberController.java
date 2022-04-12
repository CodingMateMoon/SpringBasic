package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Controller라는 어노테이션이 있으면 스프링 컨테이너라는 통에 멤버 컨트롤러 객체 생성해서 넣어둡니다
 * 스프링 컨테이너에서 스프링 빈 관리 . helloController 등
 */
@Controller
public class MemberController {

    /**
     * 스프링이 관리를 하게 되면 다 스프링 컨테이너에 등록하고 스프링 컨테이너에서 받아서 쓰도록 바꾸어야합니다. 멤버 컨트롤러말고 다른 여러 컨테이너들이
     * 멤버 서비스를 가져다 쓸 수 있습니다. 회원은 여러군데에서 쓰이기 때문에 여러개를 생성할 필요없이 하나만 만들고 공유하는 것이 효율적입니다.
     * 스프링 컨테이너에 등록하면 하나만 등록됩니다.
     *  private final MemberService memberService = new MemberService();
     */
    private final MemberService memberService;

    /**
     * autowired. MemberController. 스프링 컨테이너가 뜰 때 memberController 생성. 생성자 호출. 생성자에 Autowired가 있으면 MemberService를
     * 스프링이 스프링 컨테이너에 가져다 연결시켜줍니다.
     * org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'hello.hellospring.service.MemberService' available:
     * expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
     * MemberService는 순수한 자바코드인 상태. @Service를 넣어주어야합니다. @Service가 있으면 스프링 컨테이너에 등록. @Repository도 마찬가지.
     * Controller를 통해서 외부 요청을 받고 Service에서 비즈니스 로직을 만들고 Repository에서 데이터를 저장하는 정형화된 패턴으로 구성
     * 생성자에서 @Autowire를 쓰면 멤버컨트롤러가 생성이 될 때 스프링 빈에 등록되어 있는 멤버 서비스 객체를 가져다 넣어줍니다. DI. 의존관계 주입. 밖에서 스프링이 넣어주는 느낌.
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
