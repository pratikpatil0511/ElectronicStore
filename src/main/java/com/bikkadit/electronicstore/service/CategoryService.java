package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.helper.PageableResponse;

public interface CategoryService {

    //create

    CategoryDto create(CategoryDto categoryDto);

    //update

    CategoryDto update(String categoryId,CategoryDto categoryDto);

    //delete

    void delete(String CategoryId);

    //getAll

    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get single by id

    CategoryDto getById(String categoryId);
}
