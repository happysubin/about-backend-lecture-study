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
        Member member = new Member("0", 10000);

        repository.save(member); //h2에서 저장한 데이터를 확인했다.

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}",findMember);

        assertThat(member).isEqualTo(findMember);

        //update : money 10000원에서 20000으로
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);


        //delete
        repository.delete(member.getMemberId());
    }
}