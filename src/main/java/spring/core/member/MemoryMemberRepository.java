package spring.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store=new HashMap<>();

    //원래는 concurrent HashMap를 써야한다. 동시성 이슈로 인해서 우리는 예제라서 이걸 그낭 사용!

    @Override
    public void save(Member member) {
        store.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
