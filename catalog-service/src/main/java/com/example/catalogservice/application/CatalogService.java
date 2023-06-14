package com.example.catalogservice.application;

import com.example.catalogservice.domain.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<ResponseCatalog> getAllCatalogs(){
        return catalogRepository.findAll()
                .stream()
                .map(ResponseCatalog::of)
                .collect(Collectors.toList());
    }
}
