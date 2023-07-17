package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.ApiResponse;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static Logger logger= LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto)
    {
        logger.info("Initiated request for save Product details");
        ProductDto savedProduct = this.productService.create(productDto);
        logger.info("Completed request for save Product details");
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable String id,@Valid @RequestBody ProductDto productDto)
    {
        logger.info("Initiated request for update Product details with id: {}",id);
        ProductDto updatedProduct = this.productService.update(id, productDto);
        logger.info("Completed request for update Product details with id: {}",id);
        return new ResponseEntity<>(updatedProduct,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String productId)
    {
        logger.info("Initiated request for delete Product details with id: {}",productId);
        this.productService.delete(productId);
        logger.info("Completed request for delete Product details with id: {}",productId);
        ApiResponse apiResponse = ApiResponse.builder().message(ApiConstant.DELETE_PRODUCT + productId)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id)
    {
        logger.info("Initiated request for get Product details with id: {}",id);
        ProductDto productDto = this.productService.getById(id);
        logger.info("Completed request for get Product details with id: {}",id);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all Product's details");
        PageableResponse<ProductDto> allProduct = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all Product's details");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all live Product's details");
        PageableResponse<ProductDto> allLiveProduct = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all live Product's details");
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);
    }

    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> getBySubTitle(
            @PathVariable String subTitle,
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all live Product's details with subTitle:{} ",subTitle);
        PageableResponse<ProductDto> bySubTitle = this.productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all live Product's details with subTitle:{} ",subTitle);
        return new ResponseEntity<>(bySubTitle,HttpStatus.OK);
    }
}
