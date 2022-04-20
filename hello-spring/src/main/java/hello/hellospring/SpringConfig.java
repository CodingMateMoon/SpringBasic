package hello.hellospring;


import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 스프링이 뜰 때 Configuration을 읽고 스프링 빈을 등록하라는 뜻이네 라고 스프링이 인식합니다.
 * 과거에는 자바코드가 아닌 xml로 설정했었습니다. 지금은 거의 @Configuration, @Autowired 등 자바 코드로 설정합니다.
 * 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈을 등록하는 것이 좋습니다.
 * EX. 멤버 리포지토리 설계를 할 때 아직 데이터 저장소가 선정되지 않았다는 상황을 가정합니다.
 * 일단 메모리를 만들고 나중에 교체하기 위해 인터페이스를 설계하고 구현체로 MemoryMemberRepository를 쓰는 상황입니다.
 * 나중에 MemoryMemberRepository를 다른 Repository로 바꿔치기 할 예정인 경우 기존에 운영중인 코드를 손대지 않고 바꿔치기를 할 수 있는 방법이 있습니다.
 * 컴포넌트 스캔을 사용하면 여러 파일을 바꿔야하지만 설정 파일로 관리할 경우 한 파일만 관리하면 됩니다.
 *
 * @Autowired 를 통한 DI는 helloConroller , memberService 등과 같이 스프링이 관리하는 객체에서만 동작합니다.
 * 스프링 빈으로 등록하지 않고 직접 생성한 객체에서는 동작하지 않습니다.
 */
// Configuration 설정 파일 하나로 스프링 빈 등록 및 관리

@Configuration
public class SpringConfig {

    /* spring boot가 application.properties에 작성된 datasource url, driver 등의 정보등을 보고
    db와 연결할 수 있는 DataSource를 만들어줍니다(자체적으로 Bean 생성). @Autowired이 있으면 spring을 통해 datasource를 주입받을 수 있습니다(DI).
    * */
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


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
     * MemoryMemberRepository -> JdbcMemberRepository로 바꿀 수 있습니다.
     * @return
     * 	[Why?] 스프링을 쓰는 이유.
     * 	객체지향적 설계가 좋은 이유는 다형성을 활용할 수 있는 것입니다. interface를 두고 구현체를 바꿔 끼울 수 있습니다.
     * 	스프링은 이러한 다형성 활용을 편하게 할 수 있도록 스프링 컨테이너가 이것을 지원해줍니다. DI 덕분에 편하게 할 수 있습니다.
     * 	과거에는 MemberService가 MemberRepository를 알고있기 때문에 MemberService쪽에서는 MemoryMemberRepository에서 JdbcMemberRepository로 바꾸는 작업을 해야했습니다.
     * 	오더서비스 멤버서비스 등 여러 개이면 다 고치는 것이 필요했습니다. @Configuration을 활용하면 기존의 Service들을 수정하지 않고
     * 	오직 애플리케이션을 조립하는(설정하는) Configuration 파일만 수정하면 다른 코드들은 수정할 필요가 없습니다.
     * 	MemberService는 MemberRepository를 의존하고 있습니다. MemberRepository는 구현체로 MemoryMemberRepository와 JdbcMemberRepository가 있는데
     * 	스프링 컨테이너에서 MemoryMemberRepository대신 JdbcMemberRepository를 MemberRepository 구현체로 등록해서 기능을 바꿀 수 있었습니다.
     * 	- 개방 폐쇄 원칙 (OCP, Open-Closed Principle) : 확장(기능 추가)에는 열려있고, 수정, 변경에는 닫혀있는 것입니다.
     * 	- 객체지향의 다형성 개념을 활용하면 기능을 변경해도 애플리케이션 전체를 수정할 필요없이 조립하는 코드(Configuration)만 변경하면 됩니다. 실제 애플리케이션 동작에 필요한 코드는 건드릴 필요가 없습니다.
     * 스프링 DI(Dependencies Injection)을 사용하면 기존 코드를 손대지 않고 설정만으로 구현 클래스를 변경할 수 있습니다.
     */
    @Bean
    public MemberRepository memberRepository() {

        //return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
