package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    Category category;
    @BeforeEach
    public void init()
    {

        category = Category.builder()
                .id(UUID.randomUUID().toString())
                .title("mobile phones")
                .description("this is about all types of mobiles")
                .coverImage("mobile.jpg")
                .build();
    }
    @Test
    public void createCategoryTest()
    {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto = categoryService.create(this.modelMapper.map(category, CategoryDto.class));

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle());
    }


    @Test
    public void updateCategoryTest()
    {
        Mockito.when(categoryRepository.findById("abcd")).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        System.out.println("old title: "+category.getTitle());

        String categoryId="abcd";

        CategoryDto categoryDto = CategoryDto.builder()
                .title("smart phones")
                .build();

        CategoryDto updated = categoryService.update(categoryId, categoryDto);
        System.out.println("updated title: "+updated.getTitle());
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(categoryDto.getTitle(),updated.getTitle());

    }

    @Test
    public void deleteCategoryTest()
    {
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));

        String categoryId="xyz";
        categoryService.delete(categoryId);

        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }


    @Test
    public void getAllCategoryTest()
    {

        Category category1 = Category.builder()
                .id(UUID.randomUUID().toString())
                .title("tv")
                .description("best tv's available here")
                .coverImage("tv.jpg")
                .build();


        Category category2 = Category.builder()
                .id(UUID.randomUUID().toString())
                .title("furniture")
                .description("Best furniture for your home")
                .coverImage("furniture.jpg")
                .build();

        List<Category> categoryList = List.of(category, category1, category2);

        Page page=new PageImpl(categoryList);

        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategories = categoryService.getAll(1, 10, "title", "desc");

        Assertions.assertEquals(3,allCategories.getContent().size());
    }
}
