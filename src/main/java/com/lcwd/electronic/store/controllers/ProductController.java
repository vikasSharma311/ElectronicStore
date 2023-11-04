package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    private FileService fileService;
    @PreAuthorize("hasRole('ADMIN')")
    //create
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }// update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId) {
        productService.delete(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("product is deleted SuccessFully !!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    //getsingle
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    //getall
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //getalllive
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //searchal
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
                                                                      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //upload
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId, @RequestParam("productImage") MultipartFile image) throws IOException {
        String fileName = fileService.uploadFile(image,imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getProductImageName()).message("Product image is uploaded successFully !!").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);
        logger.info("User Image name ,{}",productDto.getProductImageName());
        InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName() );
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }




}