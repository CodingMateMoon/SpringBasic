package hello.hellospring;


import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링이 뜰 때 Configuration을 읽고 스프링 빈을 등록하라는 뜻이네 라고 스프링이 인식합니다.
 * 과거에는 자바코드가 아닌 xml로 설정했었습니다. 지금은 거의 @Configuration, @Autowired 등 자바 코드로 설정합니다.
 * 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈을 등록하는 것이 좋습니다.
 * EX. 멤버 리포지토리 설계를 할 때 아직 데이터 저장소가 선정되지 않았다는 상황을 가정합니다.
 * 일단 메모리를 만들고 나중에 교체하기 위해 인터페이스를 설계하고 구현체로 MemoryMemberRepository를 쓰는 상황입니다.
 * 나중에 MemoryMemberRepository를 다른 Repository로 바꿔치기 할 예정인 경우 기존에 운영중인 코드를 손대지 않고 바꿔치기를 할 수 있는 방법이 있습니다.
 * 컴포넌트 스캔을 사용하면 여러 파일을 바꿔야하지만 설정 파일로 관리할 경우 한 파일만 관리하면 됩니다.
 * @Autowired 를 통한 DI는 helloConroller , memberService 등과 같이 스프링이 관리하는 객체에서만 동작합니다.
 * 스프링 빈으로 등록하지 않고 직접 생성한 객체에서는 동작하지 않습니다.
 */
// Configuration 설정 파일 하나로 스프링 빈 등록 및 관리

@Configuration
public class SpringConfig {

    /**
     * memberService 로직을 호출해서 스프링 컨테이너에 스프링 빈을 등록합니다.
     * MemberService, MemberRepository 둘 다 스프링 빈으로 등록합니다. 스프링 빈으로 등록되어있는 MemberRepository를 MemberSerivce에 넣어줍니다.
     */
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    /**
     * MemberRepository는 인터페이스이므로 MemoryMemberRepository 구현체로 객체 생성 후 리턴해줍니다.
     * 나중에 @Configuration 설정 파일에 있는 MemoryMemberRepository를 DbMemberRepository등으로 바꿔치기하면 기존에 운영중인 코드를 손대지 않고 바꿔치기 할 수 있습니다.
     * @return
     */
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
