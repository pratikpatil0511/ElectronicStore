package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.PageHelper;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${product.image.path}")
    private String imagePath;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        //add Id
        String productId = UUID.randomUUID().toString();
        productDto.setId(productId);
        //add Date
        productDto.setAddedDate(new Date());
        Product product = this.modelMapper.map(productDto, Product.class);
        logger.info("Request sent to Product Repository to save Product details");
        Product savedProduct = this.productRepository.save(product);
        logger.info("Product details saved successfully");
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(String id, ProductDto productDto) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_NOT_FOUND + id));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setImageName(productDto.getImageName());
        logger.info("Request sent to Product Repository to update Product details");
        Product updatedProduct = this.productRepository.save(product);
        logger.info("Product details updated successfully");
        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_NOT_FOUND + productId));
        String fullImagePath=imagePath+product.getImageName();
        Path path=Path.of(fullImagePath);
        try
        {
            Files.delete(path);
            logger.info("Image deleted successfully : {}",product.getImageName());
        }
        catch(NoSuchFileException ex)
        {
            logger.info("Image not found in folder : {}",product.getImageName());
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }
        logger.info("Request sent to Product Repository to delete Product details with id:{} ",productId);
        this.productRepository.delete(product);
        logger.info("Product details deleted successfully with id:{} ",productId);
    }

    @Override
    public ProductDto getProductById(String id) {
        logger.info("Request sent to Product Repository to get Product details with id:{} ",id);
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_NOT_FOUND + id));
        logger.info("Product details fetched successfully with id:{} ",id);
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy,String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort);
        logger.info("Request sent to Product Repository to get all Product's details");
        Page<Product> page = this.productRepository.findAll(pageable);
        logger.info("All Product's details fetched successfully");

        PageableResponse<ProductDto> pageableResponse = PageHelper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy,String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber-1,pageSize,sort);
        logger.info("Request sent to Product Repository to get all live Product's details");
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        logger.info("All live Product's details fetched successfully");

        PageableResponse<ProductDto> pageableResponse = PageHelper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String keywords,int pageNumber, int pageSize, String sortBy,String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber-1,pageSize,sort);
        logger.info("Request sent to Product Repository to get all Product's details with subTitle:{} ",keywords);
        Page<Product> page = this.productRepository.findByTitleContaining(pageable, keywords);
        logger.info("All Product's details fetched successfully with subTitle:{} ",keywords);

        PageableResponse<ProductDto> pageableResponse = PageHelper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND + categoryId));
        //add Id
        String productId = UUID.randomUUID().toString();
        productDto.setId(productId);
        //add Date
        productDto.setAddedDate(new Date());
        Product product = this.modelMapper.map(productDto, Product.class);
        //add Category
        product.setCategory(category);
        logger.info("Request sent to Product Repository to save Product details with Category id : {}",categoryId);
        Product savedProduct = this.productRepository.save(product);
        logger.info("Product details saved successfully with Category id : {}",categoryId);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductCategory(String categoryId,String productId ) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND+categoryId));
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_NOT_FOUND+productId));
        product.setCategory(category);
        logger.info("Request sent to Product Repository to update Category in Product details");
        Product updatedProduct = this.productRepository.save(product);
        logger.info("Category updated successfully in Product details");
        return this.modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getCategoryProducts(String categoryId,int pageNumber, int pageSize, String sortBy,String sortDir) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CATEGORY_NOT_FOUND + categoryId));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy)).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        logger.info("Request sent to Product Repository to fetch Products of Category id : {}",categoryId);
        Page<Product> page = this.productRepository.findByCategory(pageable, category);
        logger.info("All Products fetched successfully of Category id : {}",categoryId);
        PageableResponse<ProductDto> pageableResponse = PageHelper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }
}
