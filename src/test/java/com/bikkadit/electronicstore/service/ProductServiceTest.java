package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
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
import org.springframework.data.domain.*;

import java.util.*;

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

    @MockBean
    private CategoryRepository categoryRepository;

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

    @Test
    public void updateProductTest()
    {
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        System.out.println("Discounted price before :"+product.getDiscountedPrice());

        String productId="0511p";

        ProductDto productDto = ProductDto.builder()
                .id(UUID.randomUUID().toString())
                .title("iPhone 14 max pro")
                .description("this is one of the best phones in market")
                .price(90000)
                .discountedPrice(75000)
                .quantity(5)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("iPhone14.jpeg")
                .build();

        ProductDto updated = productService.update(productId, productDto);
        System.out.println("Discounted price after :"+updated.getDiscountedPrice());
        System.out.println(updated.getTitle());

        Assertions.assertEquals(product.getDiscountedPrice(),updated.getDiscountedPrice());
    }

    @Test
    public void deleteProductTest()
    {
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));

        productService.delete("123pratik");

        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }

    @Test
    public void getProductById()
    {
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        String productId="125pqr";
        ProductDto productDto = productService.getById(productId);

        Assertions.assertEquals(product.getQuantity(),productDto.getQuantity());
    }

    @Test
    public void getAllProductTest()
    {
        Product product1 = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("samsung sUltra 22")
                .description("this is one of the best phones in market")
                .price(85000)
                .discountedPrice(80000)
                .quantity(25)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("sultra22.jpeg")
                .category(category)
                .build();

        Product product2 = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("Realme 8 5G")
                .description("best for your budget")
                .price(15000)
                .discountedPrice(10000)
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("realme8.jpeg")
                .category(category)
                .build();

        List<Product> productList=new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        productList.add(product2);

        Page page=new PageImpl(productList);

        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> allProduct = productService.getAll(1, 5, "price", "desc");

        Assertions.assertEquals(3,allProduct.getContent().size());
    }

    @Test
    public void searchProductByTitle()
    {
        Product product1 = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("iPhone 13 max pro")
                .description("this is one of the best phones in market")
                .price(65000)
                .discountedPrice(60000)
                .quantity(25)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("iPhone14.jpeg")
                .category(category)
                .build();

        List<Product> productList = Arrays.asList(product, product1);

        Page<Product> page=new PageImpl<>(productList);
        System.out.println(page.getTotalElements());

        String sortDir="desc";
        Sort sort;
        if(sortDir.equalsIgnoreCase("desc"))
        {
            sort = Sort.by("price").descending();
        }
        else
        {
            sort=Sort.by("price").ascending();
        }

       Pageable pageable=PageRequest.of(0, 1, sort);
       Mockito.when(productRepository.findByTitleContaining(pageable,"iPhones")).thenReturn(page);

        String keywords="iPhones";
        PageableResponse<ProductDto> searched = productService.searchByTitle(keywords, 1, 1, "price", "desc");

        Assertions.assertEquals(2,searched.getContent().size(),"size is not same : test case failed");
    }

    @Test
    public void getAllLiveProductTest()
    {

        Product product1 = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("samsung sUltra 22")
                .description("this is one of the best phones in market")
                .price(85000)
                .discountedPrice(80000)
                .quantity(25)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("sultra22.jpeg")
                .category(category)
                .build();

        Product product2 = Product.builder()
                .id(UUID.randomUUID().toString())
                .title("Realme 8 5G")
                .description("best for your budget")
                .price(15000)
                .discountedPrice(10000)
                .quantity(50)
                .addedDate(new Date())
                .stock(true)
                .live(true)
                .imageName("realme8.jpeg")
                .category(category)
                .build();

        List<Product> productList = List.of(product, product1, product2);
        Page<Product> page=new PageImpl<>(productList);
        String sortDir="desc";
        String sortBy="price";
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(0, 4, sort);

        Mockito.when(productRepository.findByLiveTrue(pageable)).thenReturn(page);

        PageableResponse<ProductDto> allLiveProducts = productService.getAllLive(1, 4, "price", "desc");

        Assertions.assertNotNull(allLiveProducts);
        Assertions.assertEquals(3,allLiveProducts.getContent().size());
    }


    @Test
    public void createProductWithCategoryTest()
    {
        Mockito.when(categoryRepository.findById("123xyz")).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        String categoryId="123xyz";

        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);

        Assertions.assertNotNull(productWithCategory);
        Assertions.assertEquals(product.getCategory().getTitle(),productWithCategory.getCategory().getTitle());

    }

    @Test
    public void updateCategoryTest()
    {
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        String categoryId="123abc";
        String productId="456xyz";
        ProductDto productDto = productService.updateCategory(categoryId, productId);

        Assertions.assertEquals(category.getCoverImage(),productDto.getCategory().getCoverImage());
    }
}
