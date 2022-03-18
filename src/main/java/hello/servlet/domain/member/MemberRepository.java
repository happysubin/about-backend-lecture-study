package hello.servlet.domain.member;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private static Map<Long, Member> store =new HashMap<>();

    private static long sequence =0L;

    private static final MemberRepository instance=new MemberRepository(); //싱글톤

    public static MemberRepository getInstance(){
        return instance;
    }

    private MemberRepository(){ //싱글톤을 위해 생성자를 막는다

    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
