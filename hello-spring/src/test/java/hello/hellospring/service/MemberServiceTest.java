package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 테스트 코드 생성 : ctrl + shift + T, 똑같은 패키지와 이름으로 생성함. 실동작코드는 관리상 한글로 적기 애매한데 테스트는 영어권 사람과 협업이 아니라면 한글로 적어도 가능.
// 빌드될때 테스트코드는 실제 코드에 포함되지 않음
// 자바 코드로 최소한의 단위로 테스트하는 것을 단위테스트라고 합니다. 스프링 컨테이너 및 DB 연동까지 하는 것을 보통 통합테스트라고 합니다.
// 가급적이면 순수한 단위테스트가 훨씬 좋은 테스트일 확률이 높습니다. 단위 단위로 쪼개서 테스트를 잘할 수 있도록 하고 스프링 컨테이너없이 테스트하는 훈련이 필요.
// 컨테이너까지 어쩔 수 없이 올려야하는 상황이면 테스트 설계가 잘못됐을 확률이 높습니다. 정말 좋은 테스트는 단위테스트를 잘 만드는것
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    // 테스트 독립적 실행. 각 테스트 실행전 MemoryMemberRepository를 만들고 MemberService에 넣어줍니다.
    // 이를 통해 같은 MemoryMemberRepository 사용 가능. DI(Dependency Injection) 개념
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    // 이전에 실행한것 그대로 실행 : shift + F10
    //각 테스트함수 진행할 때마다 clear후 다음 테스트함수를 진행해서 테스트함수를 실행하면서 변경된 데이터가 다른 테스트함수에 영향을 주는 것을 막습니다.
    @AfterEach
    public void afterEach(){

        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        //given 무언가가 주어졌을때
        Member member = new Member();
        member.setName("hello");
        /* spring으로 저장하고 clear안할경우 다음 테스트에서 spring이름의 멤버 저장할 때 바로 에러가 날 수 있습니다.
        member.setName("spring");

         */

        //when 이걸로 실행했을때. 어떤것을 검증할것인가 생각합니다.
        Long saveId = memberService.join(member);

        //then 결과가 이것이 나와야합니다.
        // 우리가 저장한 것이 "hello" 이름을 가진 객체가 맞는지 확인합니다.
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when

        // memberService.join(member2) 로직을 태울때 이 exception이 터지는것을 기대.
        
        memberService.join(member1);
        // expression assertThrows 함수 앞에서 Intoduce Variable : ctrl + alt + v, 단축키 클릭 시 반환값 저장 변수 자동으로 생성합니다.
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.assertThrows");
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

        //중복 저장하므로 Exception이 발생해서 "이미 존재하는 회원입니다" 메세지가 띄워져야하는데 e.getMessage() 결과값이 "이미 존재하는 회원입니다"와 일치해야합니다
        // 기대값은 "이미 존재하는 회원입니다."인데 결과값이 "이미 존재하는 회원입니다"라고 나와서 '.' 차이로 Error가 발생했습니다.
        /**
         * org.opentest4j.AssertionFailedError:
         * expected: "이미 존재하는 회원입니다.123"
         *  but was: "이미 존재하는 회원입니다"
         * Expected :"이미 존재하는 회원입니다.123"
         * Actual   :"이미 존재하는 회원입니다"
         */

        /*
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
        }

         */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}