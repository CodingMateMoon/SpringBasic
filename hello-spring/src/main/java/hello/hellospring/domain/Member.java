package hello.hellospring.domain;

import javax.persistence.*;


/*
jpa를 쓰려면 엔티티 매핑을 해야합니다.
gradle 설정 후 External Library에 jpa, hibernate 라이브러리가 다운로드 받아져야합니다.
jpa는 자바 진영 표준 인터페이스입니다. 구현 기술들 구현체들이 여러 가지가 있는데 hibernate가 그 중 하나입니다. jpa를 구현하는 각 업체마다 성능 편이성 등 차이가 존재합니다.
jpa는 객체와 ORM이라는 기술입니다. Object, Relational Dababase 테이블, Mapping(@ 어노테이션 활용)

javax.persistence.Entity (@Entity) : jpa가 관리하는 엔티티라고 명시합니다.
@Id : PK 매핑. PK는 DB에서 생성해주고 있습니다. insert into member(name) values('spring1') : ID : 16 , Name : Spring1 DB에서 자동으로 ID 생성해줍니다.
DB가 ID를 자동으로 생성해주는 것을 IDENTITY라고 합니다. EX) 오라클 sequence.
* */

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "username") : DB에 있는 username Column과 매핑
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
