package com.example.service;

import com.example.model.ProductDTO;
import com.example.model.ProductDetailsDTO;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;
    @Autowired
    RequestService service;

    public List<ProductDTO> findAllProductsFromCatalog(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> pagedResult = repository.findAllProductsFromCatalog(paging);
        List<ProductDTO> products = new ArrayList<>();
        if(pagedResult != null && pagedResult.hasContent()) {
            products = pagedResult.getContent();
        }
        return products;
    }
    public ProductDetailsDTO findProductBySku(String sku) throws IOException {
        ProductDetailsDTO product = repository.findProductBySku(sku);
        if (product == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found ");
        }
        return product;
    }

    public List<ProductDTO> findByNameOrSku(String nameOrSku,Integer pageNo,Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> productPage = repository.findByNameOrSku(nameOrSku,paging);
        List<ProductDTO> products = new ArrayList<>();
        if(productPage != null && productPage.hasContent()) {
            products = productPage.getContent();
        }
        return products;
    }

}
