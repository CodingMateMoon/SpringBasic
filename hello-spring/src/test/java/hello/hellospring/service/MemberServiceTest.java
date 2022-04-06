package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.Test;

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

        //when 이걸로 실행했을때. 어떤것을 검증할것인가
        Long saveId = memberService.join(member);

        //then 결과가 이것이 나와야함.
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}