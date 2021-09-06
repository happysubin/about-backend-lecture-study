package hello.hello.world.spring.repository;

import javax.persistence.EntityManager;
import hello.hello.world.spring.domain.Member;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    //jpa는 엔티티 매니저라는 것으로 모든것이 동작된다.
    //아까 우리가 build.gradle 에서 jpa 라이브러리를 가져옴. 그러면 스프링이 자동으로 엔티티 매니저를 생성 . 디비랑 스프링이랑 다연결해준다
    //그러면 만들어진걸 주입 받으면 된다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }
}
