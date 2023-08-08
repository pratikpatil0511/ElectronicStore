package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.ApiResponse;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import com.bikkadit.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static Logger logger= LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private ProductService productService;

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for save new Category details
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        logger.info("Initiated request for save Category details");
        CategoryDto savedCategory = this.categoryService.createCategory(categoryDto);
        logger.info("Completed request for save Category details");
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for update Category details
     * @param id
     * @param categoryDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String id,@Valid @RequestBody CategoryDto categoryDto)
    {
        logger.info("Initiated request for update Category details with id: {}",id);
        CategoryDto updatedCategory = this.categoryService.updateCategory(id, categoryDto);
        logger.info("Completed request for update Category details with id: {}",id);
        return new ResponseEntity<>(updatedCategory,HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for delete  Category details by using id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String id)
    {
        logger.info("Initiated request for delete Category details with id: {}",id);
        this.categoryService.deleteCategory(id);
        logger.info("Completed request for delete Category details with id: {}",id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ApiConstant.DELETE_CATEGORY + id)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get all Category's details
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.CATEGORY_TITLE,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all Category's details");
        PageableResponse<CategoryDto> pageableResponse = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all Category's details");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get Category details by using id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String id)
    {
        logger.info("Initiated request for get User details with id: {}",id);
        CategoryDto categoryDto = this.categoryService.getCategoryById(id);
        logger.info("Completed request for get User details with id: {}",id);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }


    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for save Product details with Category
     * @param productDto
     * @param categoryId
     * @return
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @Valid @RequestBody ProductDto productDto,@PathVariable String categoryId)
    {
        logger.info("Initiated request for save Product details with Category id : {}",categoryId);
        ProductDto productWithCategory = this.productService.createProductWithCategory(productDto, categoryId);
        logger.info("Completed request for save Product details with Category id: {}",categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }


    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for update Category details of Product
     * @param categoryId
     * @param productId
     * @return
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateProductCategory(@PathVariable String categoryId,@PathVariable String productId)
    {
        logger.info("Initiated request for update Category of Product : {}",productId);
        ProductDto productDto = this.productService.updateProductCategory(categoryId, productId);
        logger.info("Completed request for update Category of Product : {}",productId);
        return new ResponseEntity<>(productDto,HttpStatus.CREATED);
    }


    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get all Products of a Category
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> categoryProducts = this.productService.getCategoryProducts(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryProducts,HttpStatus.OK);
    }
}
