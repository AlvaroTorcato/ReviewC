package com.example.repository;

import com.example.model.Product;
import com.example.model.ProductDTO;
import com.example.model.ProductDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    @Query("select new com.example.model.ProductDTO(f.sku,f.name) from Product f")
    Page<ProductDTO> findAllProductsFromCatalog(Pageable paging);

    @Query("select new com.example.model.ProductDetailsDTO(f.sku,f.name,f.description) from Product f where f.sku = :sku")
    ProductDetailsDTO findProductBySku(@Param("sku") String sku);

    @Query("select new com.example.model.ProductDTO(f.sku,f.name) from Product f where f.sku like %:nameOrSku% or f.name like %:nameOrSku%")
    Page<ProductDTO> findByNameOrSku(@Param("nameOrSku") String nameOrSku, Pageable paging);

    @Query("select f from Product f where f.sku = :sku")
    Product findBySkuProduct(@Param("sku") String sku);
}
