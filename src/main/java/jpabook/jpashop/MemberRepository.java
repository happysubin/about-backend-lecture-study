package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//@Repository
public class MemberRepository {

    @PersistenceContext //엔티티 매니저를 주입해준다.
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); //커맨드와 쿼리를 분리해라?? 아직은 이해 잘 안됨
    }

    public Member find(Long id){
        return em.find(Member.class,id);
    }

}
