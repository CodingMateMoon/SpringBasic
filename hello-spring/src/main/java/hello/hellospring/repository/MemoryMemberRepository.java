package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements  MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence= 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(store.get(id));
    }  /*store.get(id)값이 null인 경우 문제 생길 수 있기때문에 Nullable처리
        return store.get(id);*/


    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // loop
                .filter(member -> member.getName().equals(name) ) // 조건 맞는 경우만 필터.
                .findAny(); //  loop 돌리며 조건 맞는 경우 찾으면 반환
    }

    @Override
    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }


    public void clearStore(){
        store.clear();
    }
}
