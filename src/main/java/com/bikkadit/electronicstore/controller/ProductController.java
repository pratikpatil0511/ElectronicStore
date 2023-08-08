package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.ApiResponse;
import com.bikkadit.electronicstore.helper.ImageResponse;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.service.FileService;
import com.bikkadit.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static Logger logger= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for save Product details
     * @param productDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto)
    {
        logger.info("Initiated request for save Product details");
        ProductDto savedProduct = this.productService.createProduct(productDto);
        logger.info("Completed request for save Product details");
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for update Product details
     * @param id
     * @param productDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id,@Valid @RequestBody ProductDto productDto)
    {
        logger.info("Initiated request for update Product details with id: {}",id);
        ProductDto updatedProduct = this.productService.updateProduct(id, productDto);
        logger.info("Completed request for update Product details with id: {}",id);
        return new ResponseEntity<>(updatedProduct,HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for delete Product details by using id
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId)
    {
        logger.info("Initiated request for delete Product details with id: {}",productId);
        this.productService.deleteProduct(productId);
        logger.info("Completed request for delete Product details with id: {}",productId);
        ApiResponse apiResponse = ApiResponse.builder().message(ApiConstant.DELETE_PRODUCT + productId)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get Product details by using id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id)
    {
        logger.info("Initiated request for get Product details with id: {}",id);
        ProductDto productDto = this.productService.getProductById(id);
        logger.info("Completed request for get Product details with id: {}",id);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get all Product's details
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all Product's details");
        PageableResponse<ProductDto> allProduct = this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all Product's details");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get all live Product's details
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all live Product's details");
        PageableResponse<ProductDto> allLiveProduct = this.productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all live Product's details");
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for search Products by subTitle
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductBySubTitle(
            @PathVariable String subTitle,
            @RequestParam(value="pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = ApiConstant.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = ApiConstant.PRODUCT_PRICE,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = ApiConstant.ASC,required = false) String sortDir
    )
    {
        logger.info("Initiated request for get all live Product's details with subTitle:{} ",subTitle);
        PageableResponse<ProductDto> bySubTitle = this.productService.searchProductByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for get all live Product's details with subTitle:{} ",subTitle);
        return new ResponseEntity<>(bySubTitle,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for upload image for Product
     * @param productId
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/productImage/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @PathVariable String productId,
            @RequestParam(value="productImage") MultipartFile file
            ) throws IOException
    {
        logger.info("Initiated request for upload image for Product with id:{} ",productId);
        String imageName = this.fileService.uploadFile(file, imagePath);
        logger.info("Completed request for upload image for Product with id:{} ",productId);
        ProductDto productDto = this.productService.getProductById(productId);
        productDto.setImageName(imageName);
        this.productService.updateProduct(productId,productDto);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(productDto.getImageName())
                .message(ApiConstant.PRODUCT_IMAGE+imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get image of Product
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/getProductImage/{id}")
    public void serveProductImage(@PathVariable String id, HttpServletResponse response) throws IOException
    {
        ProductDto productDto = this.productService.getProductById(id);
        logger.info("Product image name : {}",productDto.getImageName());
        InputStream resource = fileService.getResource(imagePath, productDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
