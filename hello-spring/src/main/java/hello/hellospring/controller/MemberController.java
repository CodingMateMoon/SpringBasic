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
     *
     *  DI(Dependency Injection)의 3가지 방법
     *  1. 생성자를 통해서 주입하는 방법 (생성자 주입)
     *  2. 생성자를 빼고 필드에다가 @Autowired를 적용하는 방법 (필드 주입)
     *  3. 생성은 생성대로 되고 setter 주입
     */

    private  MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;

        /**
         * setter Injection 사용 시 아무 개발자나 호출할 수 있도록 열려있게됩니다. 개발은 호출하지 않아야할 메서드는 호출되지 않도록 막는 것이 좋습니다.
        memberService.setMemberRepository();
         */
    }

    /*// 2. 생성자를 빼고 필드에다가 @Autowired를 적용하는 방법
    @Autowired  private  MemberService memberService;
    */

    /**
     * setterInjection 방식. setXXXX(setMemberService)를 setter라고 하는데 단점은 누군가가 멤버컨트롤러를 호출했을때 public으로 열려있어야 합니다. setMemberService로 바꿀일은 없지만
     * public으로 노출되어있고 잘못 바꾸면 문제가 생길 수 있습니다. MemberService는 애플리케이션 로딩 시점에서 조립할 때 바꾸지만 한번 세팅이 되면 바꿀 일이 없습니다.
     *
     *  private MemberService memberService;
     *
     *     @Autowired
     *     public void setMemberService(MemberService memberService) {
     *         this.memberService = memberService;
     *     }
     *
     * 그래서 요즘 권장하는 스타일은 생성자를 통해서 주입하는 것입니다.
     * 처음에 애플리케이션이 조립된다고 표현하는데 조립 시점에 스프링 컨테이너에 스프링 빈을 등록하고 세팅하는 것으로 끝내는 것입니다.
     * 조립 시점에 생성자로 한번만 조립하고 끝을 낸 후 바꾸지 못하도록 막는 것입니다.
     *
      */

    /**
     * autowired. MemberController. 스프링 컨테이너가 뜰 때 memberController 생성. 생성자 호출. 생성자에 Autowired가 있으면 MemberService를
     * 스프링이 스프링 컨테이너에 가져다 연결시켜줍니다.
     * org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'hello.hellospring.service.MemberService' available:
     * expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
     * MemberService는 순수한 자바코드인 상태. @Service를 넣어주어야합니다. @Service가 있으면 스프링 컨테이너에 등록. @Repository도 마찬가지.
     * Controller를 통해서 외부 요청을 받고 Service에서 비즈니스 로직을 만들고 Repository에서 데이터를 저장하는 정형화된 패턴으로 구성
     * 생성자에서 @Autowire를 쓰면 멤버컨트롤러가 생성이 될 때 스프링 빈에 등록되어 있는 멤버 서비스 객체를 가져다 넣어줍니다. DI. 의존관계 주입. 밖에서 스프링이 넣어주는 느낌.
     */

    /** 1. 생성자를 통해서 주입하는 방법
     * @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    */

}
