package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 테스트 코드 생성 : ctrl + shift + T, 똑같은 패키지와 이름으로 생성함. 실동작코드는 관리상 한글로 적기 애매한데 테스트는 영어권 사람과 협업이 아니라면 한글로 적어도 가능.
// 빌드될때 테스트코드는 실제 코드에 포함되지 않음
class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void 회원가입() {

        //given 무언가가 주어졌을때
        Member member = new Member();
        member.setName("hello");

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

        //중복 저장하므로 Exception이 발생해서 "이미 존재하는 회원입니다" 메세지가 띄워져야하는데 e.getMessage() 결과값이 "이미 존재하는 회원입니다"와 일치해야합니다
        // 기대값은 "이미 존재하는 회원입니다."인데 결과값이 "이미 존재하는 회원입니다"라고 나와서 '.' 차이로 Error가 발생했습니다.
        /**
         * org.opentest4j.AssertionFailedError:
         * expected: "이미 존재하는 회원입니다."
         *  but was: "이미 존재하는 회원입니다"
         * Expected :"이미 존재하는 회원입니다."
         * Actual   :"이미 존재하는 회원입니다"
         */
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}