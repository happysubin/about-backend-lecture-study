package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @BeforeEach
    public void beforeEach(){
        query = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        Member findMember = em.createQuery("select m from Member m where m.username = :username ", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

//    @Test
//    public void startQuerydsl() {
//        //given
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        QMember m = new QMember("m");   참고: 같은 테이블을 조인해야 하는 경우가 아니면 기본 인스턴스를 사용하자
//
//        Member findMember = query
//                .select(m)
//                .from(m)
//                .where(m.username.eq("member1")) //파라미터 바인딩
//                .fetchOne();
//
//        assertThat(findMember.getUsername()).isEqualTo("member1");
//    }

    @Test
    public void startQuerydsl() {
        Member findMember = query
                .select(member) //스태틱 임포트를 권장
                .from(member)
                .where(member.username.eq("member1")) //파라미터 바인딩
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        Member findMember = query.selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void searchAndParam() {
        List<Member> result1 = query
                .selectFrom(member)
                .where(member.username.eq("member1"),
                        null, //이렇게 null이 들어가면 null을 무시한다.
                        member.age.eq(10))
                .fetch();
        assertThat(result1.size()).isEqualTo(1);
    }


    /**
     * 강의 자료에 웬만한 조건들 다 나와있으니 참고하면 될 듯.
     * fetch, fetchOne, fetchFirst 등 결과 조회도 나와 있다.
     */

    @Test
    public void sort(){
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = query
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }



    @Test
    public void paging1() {
        List<Member> result = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1) //0부터 시작(zero index)
                .limit(2)
                .fetch();//최대 2건 조회

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void paging2() {
        QueryResults<Member> queryResults = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults(); //deprecated 되었다. 사용 x. 그냥 실습이라한거다.
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    @Test
    void aggregation(){
        List<Tuple> result = query.select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    //실무에서는 tuple 보다는 dto를 직접 뽑는다고 한다.

    @Test
    public void group() throws Exception{
        List<Tuple> result = query
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }
    //groupBy , 그룹화된 결과를 제한하려면 having

    //.groupBy(item.price)
    //.having(item.price.gt(1000))


    @Test
    void join(){
        List<Member> result = query
                .selectFrom(member)
                .join(member.team, team)  //join() , innerJoin() : 내부 조인(inner join)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    //leftJoin() : left 외부 조인(left outer join)
    //rightJoin() : rigth 외부 조인(rigth outer join)
    //JPQL의 on과 성능 최적화를 위한 fetch 조인 제공

    @Test
    void theta_join(){  //세타조인이란 연관관계 없어도 조인해버리는 것
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Member> result = query
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");

        //from 절에 여러 엔티티를 선택해서 세타 조인
        //외부조인불가능 조인 on을 사용하면 외부 조인 가능
    }

    //ON절을 활용한 조인(JPA 2.1부터 지원)
    //1. 조인대상필터링
    //2. 연관관계없는엔티티외부조인

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'teamA'
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='teamA'
     */

    @Test
    void join_on_filtering(){
        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple); //left outer join 결과가 나타난다. 따라서 정말 외부조인이 필요할 때만 사용하자. 내부조인이면 where절로 해결하자.
        }
    }

    /**
     *2. 연관관계 없는 엔티티 외부 조인
     *예)회원의 이름과 팀의 이름이 같은 대상 외부 조인
     * JPQL: SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.username = t.name
     *
     */

    @Test
    void join_on_no_relation(){ //여기서 자주 사용된다고 한다.
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("t=" + tuple);
        }
    }
    /**
     * leftJoin() 부분에 일반 조인과 다르게 엔티티 하나만 들어간다.
     * 일반조인: leftJoin(member.team, team) on조인: from(member).leftJoin(team).on(xxx)
     */

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void noFetchJoin(){
        em.flush();
        em.clear();

        //현재 지연로딩으로 멤버만 select 된다.
        Member findMember = query.selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    void useFetchJoin(){
        em.flush();
        em.clear();

        //현재 지연로딩으로 멤버만 select 된다.
        Member findMember = query.selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    //join(), leftJoin() 등 조인 기능 뒤에 fetchJoin() 이라고 추가하면 된다.


    //서브 쿼리
    //com.querydsl.jpa.JPAExpressions 사용

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery(){

        QMember memberSub = new QMember("memberSub");

        List<Member> result = query.selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions.select(memberSub.age.max()).from(memberSub)
                )).fetch();

        assertThat(result).extracting("age").containsExactly(40);

    }

    /**
     *나이가 평균 나이 이상인 회원
     */
    @Test
    void subQueryGoe(){
        QMember memberSub = new QMember("memberSub");
        List<Member> result = query.selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                )).fetch();

        assertThat(result).extracting("age").containsExactly(30,40);
    }

    /**
     * 서브쿼리 여러 건 처리, in 사용
     */

    @Test
    void subQueryIn(){
        QMember memberSub = new QMember("memberSub");

        List<Member> result = query
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                )) .fetch();

        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }

    @Test
    void selectSubQuery(){

        QMember memberSub = new QMember("memberSub");


        List<Tuple> fetch = query.select(member.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : fetch) {
            System.out.println("username = " + tuple.get(member.username));
            System.out.println("age = " + tuple.get(JPAExpressions.select(memberSub.age.avg()).from(memberSub)));
        }
    }

    /**
     * from 절의 서브쿼리 한계
     * JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리(인라인 뷰)는 지원하지 않는다. 당연히 Querydsl 도 지원하지 않는다.
     * 하이버네이트 구현체를 사용하면 select 절의 서브쿼리는 지원한다. Querydsl도 하이버네이트 구현체를 사용하면 select 절의 서브쿼리를 지원한다.
     * select 절, where 절에서 사용. from 절에서는 사용 불가.
     *
     * from 절의 서브쿼리 해결방안
     * 1. 서브쿼리를 join으로 변경한다. (가능한 상황도 있고, 불가능한 상황도 있다.)
     * 2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
     * 3. nativeSQL을 사용한다.
     *
     */


    @Test
    void caseV1(){
        List<String> result = query.from(member)
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타")).from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    //v1 보다 복잡한 경우
    @Test
    void caseV2(){
        List<String> result = query
                .select(new CaseBuilder() //복잡한 경우에는 이것을 사용
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void caseV3(){
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.age.between(0, 20)).then(2)
                .when(member.age.between(21, 30)).then(1)
                .otherwise(3);

        List<Tuple> result = query
                .select(member.username, member.age, rankPath)
                .from(member)
                .orderBy(rankPath.desc())
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            Integer rank = tuple.get(rankPath);
            System.out.println("username = " + username + " age = " + age + " rank = " + rank); }
    }
    //디비는 그룹핑, 필터링 정말 최소한으로. 이런 전환하고 바꾸는 로직은 데베 말고 애플리케이션에서 진행하자.


    //상수가 필요하면 Expressions.constant(xxx) 사용
    @Test
    void constant(){
        List<Tuple> result = query
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void concat(){
        String result = query
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        System.out.println("result = " + result);
    }

    //참고: member.age.stringValue() 부분이 중요한데, 문자가 아닌 다른 타입들은 stringValue() 로 문자로 변환할 수 있다. 이 방법은 ENUM을 처리할 때도 자주 사용한다.

}
