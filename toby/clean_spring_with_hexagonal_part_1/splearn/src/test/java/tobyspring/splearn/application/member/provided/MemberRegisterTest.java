package tobyspring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
// @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) 이거 대신 junit-platform.properties 에 정의
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        System.out.println("member = " + member);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> {
            memberRegister.register(MemberFixture.createMemberRegisterRequest());
        }).isInstanceOf(DuplicationEmailException.class);
    }


    @Test
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());

        entityManager.flush();
        entityManager.clear();


        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void deactivate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());

        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());


        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("Peter", "toby100", "자기소개");
        var savedMember = memberRegister.updateInfo(member.getId(), request);

        entityManager.flush();
        entityManager.clear();

        assertThat(savedMember.getNickname()).isEqualTo(request.nickname());
        assertThat(savedMember.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(savedMember.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Peter", "toby100", "자기소개"));

        Member member2 = registerMember("toby@splearn.app");
        memberRegister.activate(member2.getId());

        entityManager.flush();
        entityManager.clear();

        // member2는 기존의 member와 같은 profile을 사용할 수 없다.
        assertThatThrownBy(() ->{
            memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("James", "toby100", "introduction"));
        }).isInstanceOf(DuplicationProfileException.class);

        // 다른 프로필 주소로는 변경 가능
        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("James", "toby101", "introduction"));

        // 기존 프로필 주소를 바꾸는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Peter", "toby100", "자기소개"));

        // 프로필 주소 제거 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Peter", "", "자기소개"));

        // 프로필 주소 중복 허용 불가
        assertThatThrownBy(() ->{
            memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("James", "toby101", "introduction"));
        }).isInstanceOf(DuplicationProfileException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        checkInvalidRequest(new MemberRegisterRequest("subin", "Subin", "longSecret"));
        checkInvalidRequest(new MemberRegisterRequest("subin@splearn.app", "Subi", "longSecret"));
        checkInvalidRequest(new MemberRegisterRequest("subin@splearn.app", "Subin", "secret"));
    }

    private void checkInvalidRequest(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> {
            memberRegister.register(invalid);
        }).isInstanceOf(ConstraintViolationException.class);
    }
}
