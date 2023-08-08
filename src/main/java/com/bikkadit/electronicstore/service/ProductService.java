package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.PageableResponse;

import java.util.List;

public interface ProductService {

    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(String id, ProductDto productDto);

    //delete
    void deleteProduct(String productId);

    //get single
    ProductDto getProductById(String id);

    //get all
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy,String sortDir);

    //getAllLive
    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy,String sortDir);

    //search
    PageableResponse<ProductDto> searchProductByTitle(String keywords,int pageNumber, int pageSize, String sortBy,String sortDir);

    //create Product with Category
    ProductDto createProductWithCategory(ProductDto productDto,String categoryId);

    //update Category of Product
    ProductDto updateProductCategory(String categoryId,String productId);

    PageableResponse<ProductDto> getCategoryProducts(String categoryId,int pageNumber, int pageSize, String sortBy,String sortDir);
}
