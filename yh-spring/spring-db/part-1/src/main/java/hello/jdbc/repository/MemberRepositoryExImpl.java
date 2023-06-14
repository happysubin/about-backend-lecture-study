package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

public class MemberRepositoryExImpl implements MemberRepositoryEx{

    //몽땅 SQLException을 적어준다.
    @Override
    public Member save(Member member) throws SQLException {
        return null;
    }

    @Override
    public Member findById(String memberId) throws SQLException {
        return null;
    }

    @Override
    public void update(String memberId, int money) throws SQLException {

    }

    @Override
    public void delete(String memberId) throws SQLException {

    }
}
