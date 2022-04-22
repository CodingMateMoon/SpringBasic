package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    /*
    build.gradle에서 implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 라이브러리를 받으면 application.properties에서 명시한 내역들과
    데이터베이스 커넥션 정보들을 조합하여 스프링 부트가 EntityManager를 자동으로 생성해줍니다. 현재 데이터베이스와 연결해서 만들어주고 이를 통해 injection 받으면 됩니다.
    jpa는 EntityManager로 모든게 동작합니다.
    * */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        /*
        jpa가 insert 쿼리를 만들어서 DB에 집어넣고 ID까지 세팅해줍니다. 
        * */
         em.persist(member);
         return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 조회할 타입과 식별자를 인자로 넣어줍니다
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /*
    저장 조회 업데이트 같은 경우 sql을 짤 필요없습니다. PK를 통해 단건으로 찾는 findById 같은 경우 함수 호출로 간단하게 가져오지만
    findByName이나 여러개의 리스트를 가지고 돌리는 findAll 같은 경우 PK기반이 아닌 나머지들은 JPQL을 작성해야합니다.
    JPA기술을 스프링에 한번 감싸서 제공하는 기술인 SPRING DATA JPA를 사용하면 findByName, findAll도 jpql을 안짜도 됩니다.
    * */

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
         /*
        pk인 id가 아닌 name같은 경우 jpql이라는 객체지향쿼리를 사용해야하는데 sql과 유사하지만 차이점이 있습니다.
        shift x 2 -> inline Variable검색
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result
        sql은 테이블을 대상으로 쿼리를 날리지만 jpql은 객체(멤버 엔티티)를 대상으로 쿼리를 날리면 sql로 번역됩니다.
        select m from Member m은 select m from Member as m (m 별칭)과 같습니다. select의 대상은 sql의 경우 *, m.id 등 명시를 하지만
        jpql은 m을 통해 객체 자체를 select합니다. sql은 id, name 찾고 매핑하는것이 필요하지만 이를 생략할 수 있습니다.
        * */
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
