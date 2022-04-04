package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원가입
     * 같은 이름이 있는 회원은 안됨
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        Optional<Member> byName = memberRepository.findByName(member.getName());
        memberRepository.save(member);
        return member.getId();
    }
}
