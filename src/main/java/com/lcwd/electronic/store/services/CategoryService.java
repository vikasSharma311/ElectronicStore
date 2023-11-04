package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CatergoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

import java.util.List;

public interface CategoryService {
    //create
    CatergoryDto create(CatergoryDto catergoryDto);

    //update
    CatergoryDto update(CatergoryDto catergoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    // get all
    PageableResponse<CatergoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getSingle category detail

    CatergoryDto get( String categoryId);


}
