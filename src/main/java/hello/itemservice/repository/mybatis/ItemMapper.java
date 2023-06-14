package hello.itemservice.repository.mybatis;



//mybatis는 관례상 매퍼라고 한다.

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


//관례상 매퍼라고한다. 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스.

@Mapper //스프링 빈이라고 인식
public interface ItemMapper {
    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);
}
