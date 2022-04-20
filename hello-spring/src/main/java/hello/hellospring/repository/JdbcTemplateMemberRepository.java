package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        /*
        SimpleJdbcInsert는 JdbcTemplate을 넘겨서 만드는 클래스 입니다. 이를 활용시 withTableName에서 테이블 이름을 넘겨주고
        usingGeneratedKeyColumns로 insert할 데이터의 id 정보를 가지고 올 수 있도록 설정한 뒤 insert문을 수행하고 들어간
        데이터의 id 정보를 member 객체에 담아서 return합니다 document 참조.
        * */
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    /*
    [Why?] Template인 이유?
    디자인 패턴 중 템플릿 메서드가 존재합니다. 그 개념이 들어가있습니다.

    org.springframework.dao.DataIntegrityViolationException: StatementCallback; SQL [select * from member where id = ?]; Parameter "#1" is not set [90012-200];
    nested exception is org.h2.jdbc.JdbcSQLDataException: Parameter "#1" is not set [90012-200].
    jdbcTemplate.query("select * from member where id = ?", memberRowMapper()) 에서 id값 안넣어주면 위와 같은 에러가 발생할 수 있습니다.
    * */
    @Override
    public Optional<Member> findById(Long id) {

        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // 객체 생성은 RowMapper쪽에서 처리합니다.
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
