package hello.jdbc.repository;


import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource을 사용해서 저장할 것이다. 또한JdbcUtils 사용
 */

@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV1 {

    private final DataSource dataSource;


    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null; // 이걸 가지고 DB에 쿼리를 날린다.

        try{
            con = getConnection(); //커넥션을 가져온다.
            pstmt = con.prepareStatement(sql); //데이터베이스에 전달할 SQL과 파라미터로 전달할 데이터들을 준비한다.

            //여기서 SQL 파라미터 바인딩을 진행한다.
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());

            pstmt.executeUpdate();

            /**
             * Statement 를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달한다.
             * 참고로 executeUpdate() 은 int 를 반환하는데 영향받은 DB row 수를 반환한다. 여기서는 하나의 row를 등록했으므로 1을 반환한다.
             */

            return member;
        }catch (SQLException e){
            log.error("db error", e);
            throw e;
        }
        finally{
            //안 닫으면 리소스 누수 발생
            //pstmt.close(); //근데 여기서 문제가 터지면 con이 닫히지 않는다. 고로 이것은 또 try catch 문으로 감싸줘야 한다..
            //con.close();

            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

       Connection con = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try{
           con = getConnection();
           pstmt = con.prepareStatement(sql);

           pstmt.setString(1, memberId);
           rs = pstmt.executeQuery();//query는 조회, update는 생성, 삭제, 수정

           if(rs.next()){
               Member member = new Member();
               member.setMemberId(rs.getString("member_id"));
               member.setMoney(rs.getInt("money"));
               return member;
           }
           else{ //조회한 값이 없을
               throw new NoSuchElementException("member not found memberId = " + memberId );
           }
       }
       catch (SQLException e){
           log.error("db error", e);
           throw e;
       }
       finally{
           close(con, pstmt, null);
       }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where  member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);
        }
        catch(SQLException e){
            log.error("db error", e);
            throw e;
        }
        finally{
            close(con, pstmt, null);
        }

    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);
        }
        catch(SQLException e){
            log.error("db error", e);
            throw e;
        }
        finally{
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}",con, con,getClass());
        return con;
    }
}
