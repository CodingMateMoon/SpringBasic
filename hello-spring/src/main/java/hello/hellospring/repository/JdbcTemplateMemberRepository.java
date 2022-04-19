package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    /*
    * 생성자가 하나만 있으면 Spring bean으로 등록되는데 @Autowired를 생략할 수 있습니다.
    * */

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member save(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {

        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper());
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    private RowMapper<Member> memberRowMapper() {

        /* 람다식으로 변경 가능합니다. Replace with lambda.
        @FunctionalInterface 활용해서 (rs, rowNum) ResultSet, int를 매개변수로 받고 mapRow 라는 이름의 함수를 구현합니다.

        @FunctionalInterface
        public interface RowMapper<T> {
            @Nullable
            T mapRow(ResultSet rs, int rowNum) throws SQLException;
	    }
        * return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {

                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        }

        Result
        *
        * */
        return (rs, rowNum) -> {

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
