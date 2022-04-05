package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// 회원 서비스 개발. Optional 활용 Null 처리
public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 서비스 비즈니스에 가까운 용어 회원가입 이상해요->무슨 문제인지 찾아보자 . 비즈니스 의존적. Repository
    /**
     * 회원가입
     * 같은 이름이 있는 회원은 안됨
     */
    public Long join(Member member) {

        /**
         *
         // 같은 이름이 있는 중복 회원X
         // Optional 안에 멤버객체 존재. NULL일 가능성이 있는 경우 Optional을 감싸서 받음
         Optional<Member> result = memberRepository.findByName(member.getName());
         // ifPresent 저장된 값이 있으면 true, 아니면 false
         result.ifPresent(m -> {
         throw new IllegalStateException("이미 존재하는 회원입니다.");
         });
         */

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // findByName으로 가져오는 경우 메소드로 관리하는게 좋음. 서비스는 비즈니스 로직 처리
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
