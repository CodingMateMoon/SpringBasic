package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 회원 서비스 개발. Optional 활용 Null 처리

/**
 * @Documented
 * @Component public @interface Service
 * Service안에는 @Component 어노테이션이 존재합니다. Component 스캔이라고 부릅니다. 객체를 하나씩 스프링 컨테이너에 등록
 * @Component 어노테이션이 있으면 스프링 빈으로 자동 등록됩니다.
 * @Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록됩니다 .@Controller, @Service,@Repository
 * Autowired는 연관관계를 의미.  (자동 의존관계를 설정합니다.)
 * @Service 에너테이션이 없는 경우
 * Parameter 0 of constructor in hello.hellospring.controller.MemberController required a bean of type 'hello.hellospring.service.MemberService' that could not be found.
 * Consider defining a bean of type 'hello.hellospring.service.MemberService' in your configuration 오류가 발생합니다
 */


/*
스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을
커밋합니다. 만약 런타임 예외가 발생하면 롤백합니다.
JPA 사용 시 주의점. JPA는 모든 데이터 변경이 Transaction안에서 실행되어야합니다. 그래서 @Transacional이 있어야합니다. 서비스계층에 @Transaction을 명시합니다.
회원가입할 때 필요하니까 public Long join(Member member) { 함수 위에만 @Transaction을 붙여주어도 됩니다.
* */

@Transactional
public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    // MemberServiceTest에서 같은 인스턴스를 쓰기 위해 MemberRepository를 외부에서 받아서 사용하도록 변경합니다

    /*private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    private  MemberRepository memberRepository;


    /**
     * 멤버서비스는 멤버리포지토리가 필요
     *
     * @Service를 보고 스프링 컨테이너에 등록하면서 생성자 호출.
     * 생성자에 있는 @Autowired를 보고 스프링 컨테이너에 있는 MemberRepository를 넣어줍니다. MemberRepository는 MemoryMemberRespository가 구현하고 있으므로
     * 해당 리포지토리 구현체를 서비스에 주입
     */

    /*
    @Service
    public class MemberService { 다음과 같이 MemberService가 @Service Annotation으로 등록되어있지 않으면 @Autowired public MemberService가
    동작하지 않습니다. 직접 new로 객체를 생성하는 경우에도 @Autowired가 동작하지 않습니다. (직접 생성했기 때문)

     */
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    public static void main(String[] args) {
        MemberService memberService = new MemberService();
    }

     */

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

        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member);

            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }

    private void validateDuplicateMember(Member member) {
        // findByName으로 가져오는 경우 메소드로 관리하는게 좋음. 서비스는 비즈니스 로직 처리
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {

            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
