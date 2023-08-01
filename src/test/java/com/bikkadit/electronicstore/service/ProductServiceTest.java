package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    static Category category;

    Product product;

    @BeforeAll
    public static void forCategory()
    {
        category = Category.builder()
                .id(UUID.randomUUID().toString())
                .title("smart phones")
                .description("best specifications")
                .coverImage("mobiles.png")
                .build();
    }

    @BeforeEach
    public void init()
    {
        product = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("iPhone 14 max pro")
                .description("this is one of the best phones in market")
                .price(90000)
                .discountedPrice(85000)
                .quantity(5)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("iPhone14.jpeg")
                .category(category)
                .build();
    }

    @Test
    public void createProductTest()
    {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto = productService.create(this.modelMapper.map(product, ProductDto.class));

        System.out.println(productDto.getTitle());
        Assertions.assertEquals(product.getId(),productDto.getId());
    }
}
