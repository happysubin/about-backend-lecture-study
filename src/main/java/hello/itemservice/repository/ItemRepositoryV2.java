package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//이건 자동으로 빈으로 등록된다.
public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {

}
