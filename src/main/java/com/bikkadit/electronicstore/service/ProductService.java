package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.PageableResponse;

public interface ProductService {

    //create

    ProductDto create(ProductDto productDto);

    //update

    ProductDto update(String id, ProductDto productDto);

    //delete

    void delete(String productId);

    //get single

    ProductDto getById(String id);

    //get all

    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy,String sortDir);

    //getAllLive

    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy,String sortDir);

    //search

    PageableResponse<ProductDto> searchByTitle(String keywords,int pageNumber, int pageSize, String sortBy,String sortDir);
}
