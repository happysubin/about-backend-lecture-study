package com.example.catalogservice;

import com.example.catalogservice.domain.Catalog;
import com.example.catalogservice.domain.CatalogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CatalogServiceApplicationTests {

	@Autowired
	CatalogRepository catalogRepository;


	@Rollback(value = false)
	@Commit
	@Test
	void contextLoads() {
		Catalog berlin = new Catalog("CATALOG-001", "Berlin", 100, 1500);
		Catalog tokyo = new Catalog("CATALOG-002", "Tokyo", 110, 2500);
		Catalog stockholm = new Catalog("CATALOG-003", "Stockholm", 120, 3500);

		catalogRepository.saveAll(List.of(berlin, tokyo, stockholm));
	}

}
