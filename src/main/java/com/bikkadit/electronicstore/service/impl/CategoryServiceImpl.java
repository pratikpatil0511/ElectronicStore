package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.PageHelper;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setId(categoryId);
        Category category = this.modelMapper.map(categoryDto, Category.class);
        logger.info("Request sent to Category Repository to save Category details");
        Category savedCategory = this.categoryRepository.save(category);
        logger.info("Category details saved successfully");
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(String categoryId, CategoryDto categoryDto) {
        Category category = this.categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND+categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription((categoryDto.getDescription()));
        category.setCoverImage(categoryDto.getCoverImage());
        logger.info("Request sent to Category Repository to update Category details");
        Category updatedCategory = this.categoryRepository.save(category);
        logger.info("Category details updated successfully");
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND+categoryId));
        logger.info("Request sent to Category Repository to delete Category details with id: {}",categoryId);
        this.categoryRepository.delete(category);
        logger.info("Category details deleted successfully with id: {}",categoryId);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        //by ternary operator
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        logger.info("Request sent to Category Repository to get all Category's details");
        Page<Category> page = this.categoryRepository.findAll(pageable);
        logger.info("All Category's details fetched successfully");

        PageableResponse<CategoryDto> pageableResponse = PageHelper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getById(String categoryId) {
        logger.info("Request sent to Category Repository to get Category details with id: {}",categoryId);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND));
        logger.info("Category details fetched successfully with id: {}",categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
