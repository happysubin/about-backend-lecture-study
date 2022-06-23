package hello.itemservice.config;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemQueryRepositoryV2;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemRepositoryV2;
import hello.itemservice.repository.jpa.SpringDataJpaItemRepository;
import hello.itemservice.repository.jpa.SpringDataJpaItemRepository2;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import hello.itemservice.service.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepository() );
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepository(){
        return new ItemQueryRepositoryV2(em);
    }
}
