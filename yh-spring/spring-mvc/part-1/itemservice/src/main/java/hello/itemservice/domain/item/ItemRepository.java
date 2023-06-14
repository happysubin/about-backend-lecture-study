package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>(); //static 사용. 동시성 문제로 실무에서는 컨커런트 해쉬맵 사용
    private static long sequence = 0L; //static 사용

    public Item save(Item item){
        item.setId(sequence++);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId,Item updateParam){//정석은 DTO 파라미터 객체를 만들어서 거기에다가 업데이트 데이터를 넣어서 전달
        Item findItem=findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }


    public void clearStore() {
        store.clear();
    }
}
