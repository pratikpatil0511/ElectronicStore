package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.helper.PageableResponse;

public interface CategoryService {

    //create

    CategoryDto createCategory(CategoryDto categoryDto);

    //update

    CategoryDto updateCategory(String categoryId,CategoryDto categoryDto);

    //delete

    void deleteCategory(String CategoryId);

    //getAll

    PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get single by id

    CategoryDto getCategoryById(String categoryId);
}
