package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);
    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
    public void delete(String productId);
    //getSingle
    ProductDto get(String productId);

    //getall
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search Product
    PageableResponse<ProductDto>  searchByTitle(String subtitle,int pageNumber, int pageSize, String sortBy, String sortDir);
    public ProductDto createWithCategory(ProductDto productDto,String categoryId);
    //update category of product
    ProductDto updateCategory(String productId,String categoryId);
    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy,String sortDir);
}
