package tobyspring.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberStatus;

import static tobyspring.splearn.domain.member.MemberFixture.*;


// DataJpaTest 를 사용하면 h2 임베디드 데이터베이스를 사용
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void registerMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        Assertions.assertThat(member.getId()).isNull();

        memberRepository.save(member);

        Assertions.assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = memberRepository.findById(member.getId()).orElseThrow();

        Assertions.assertThat(found.getStatus()).isEqualTo(MemberStatus.PENDING);
        Assertions.assertThat(found.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        Assertions.assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}