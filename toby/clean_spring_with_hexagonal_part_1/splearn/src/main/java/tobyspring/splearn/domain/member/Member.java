package tobyspring.splearn.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import tobyspring.splearn.domain.shared.Email;
import tobyspring.splearn.domain.*;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString(callSuper = true, exclude = "detail")
@NaturalIdCache // NaturalId 를 적용하면 비즈니스 키를 가지고 영속성 컨텍스트 캐시 사용
public class Member extends AbstractEntity {
    @NaturalId // 비즈니스 키 검증에 유용.
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberDetail detail;

    protected Member() {}

    public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder encoder) {
        Member member = new Member();

        member.email = new Email(registerRequest.email());
        member.nickname = Objects.requireNonNull(registerRequest.nickname());
        member.passwordHash = Objects.requireNonNull(encoder.encode(registerRequest.password()));

        member.status = MemberStatus.PENDING;

        member.detail = MemberDetail.create();

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
        this.detail.setActivatedAt();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String secret, PasswordEncoder encoder) {
        return encoder.matches(secret, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest request) {
        this.nickname = Objects.requireNonNull(request.nickname());
        this.detail.updateInfo(request);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder encoder) {
        this.passwordHash = encoder.encode(Objects.requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
