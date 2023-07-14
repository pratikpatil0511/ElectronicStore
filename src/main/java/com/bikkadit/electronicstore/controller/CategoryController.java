package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.helper.ApiResponse;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryService;
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

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto)
    {
        logger.info("Initiated request for save Category details");
        CategoryDto savedCategory = this.categoryService.create(categoryDto);
        logger.info("Completed request for save Category details");
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable String id,@Valid @RequestBody CategoryDto categoryDto)
    {
        logger.info("Initiated request for update Category details with id: {}",id);
        CategoryDto updatedCategory = this.categoryService.update(id, categoryDto);
        logger.info("Completed request for update Category details with id: {}",id);
        return new ResponseEntity<>(updatedCategory,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id)
    {
        logger.info("Initiated request for delete Category details with id: {}",id);
        this.categoryService.delete(id);
        logger.info("Completed request for delete Category details with id: {}",id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ApiConstant.DELETE_CATEGORY + id)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.CATEGORY_TITLE,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all Category's details");
        PageableResponse<CategoryDto> pageableResponse = this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all Category's details");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable String id)
    {
        logger.info("Initiated request for get User details with id: {}",id);
        CategoryDto categoryDto = this.categoryService.getById(id);
        logger.info("Completed request for get User details with id: {}",id);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
