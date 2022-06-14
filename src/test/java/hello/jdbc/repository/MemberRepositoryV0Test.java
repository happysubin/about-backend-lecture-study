package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;


@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("v021", 10000);

        repository.save(member); //h2에서 저장한 데이터를 확인했다.

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}",findMember);

        assertThat(member).isEqualTo(findMember);
    }
}