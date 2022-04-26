package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
인터페이스는 다중상속이 가능합니다.
JpaRepository <T : Member, ID : Entity 식별자 id의 타입>
Spring data Jpa는 JpaRepository를 받고 있는 인터페이스가 있는 경우 구현체를 자동으로 만들어줍니다. 스프링 빈을 자동으로 등록해줍니다.

 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
