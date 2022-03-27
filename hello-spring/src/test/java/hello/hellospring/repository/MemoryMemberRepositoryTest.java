package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// 회원 respository 테스트 케이스 작성
public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트가 진행되고 끝날때마다 repository를 초기화해서 다음 테스트에 영향을 주지 않도록 함. 테스트는 순서, 의존관계없이 설계되야 하나의 테스트 끝날때마다 저장소 초기화 필요
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result);
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        // spring2 이름을 가진 member객체를 찾은 결과값(member2)과 member1을 비교할 경우 Expected : member1, Actual : member2
        // 실제 가져온 결과값은 member2 이지만 기대값는 member1이어서 org.opentest4j.AssertionFailedError 발생
        /*
        System.out.println(member1);
        Member result = repository.findByName("spring2").get();
        assertThat(result).isEqualTo(member1);
         */
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);


        /* Expected : 3,  Actual : 2 -> 기대값은 3이지만 실제 사이즈는 2이므로 org.opentest4j.AssertionFailedError 발생
        assertThat(result.size()).isEqualTo(3);
         */
    }


}
