package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/categories")
public class CategoryController {
    private Logger  logger= LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @Value("${category.image.path}")
    private String coverimagePath;
    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<CatergoryDto> create( @Valid @RequestBody CatergoryDto catergoryDto){
        CatergoryDto catergoryDto1 = categoryService.create(catergoryDto);
        return new ResponseEntity<>(catergoryDto1, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CatergoryDto> update(@RequestBody CatergoryDto catergoryDto,
                                               @PathVariable String categoryId){
        CatergoryDto updatedcatergory = categoryService.update(catergoryDto, categoryId);

        return new ResponseEntity<>(updatedcatergory,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Category is dleted SuccessFully !!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);

    }
    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CatergoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
            ){
        PageableResponse<CatergoryDto>pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CatergoryDto> getSingle(@PathVariable String categoryId){
        CatergoryDto catergoryDto = categoryService.get(categoryId);
        return new ResponseEntity<>(catergoryDto,HttpStatus.OK);

    }
    @PostMapping("/coverimage/{categoryId}")
    public ResponseEntity<ImageResponse>uploadCategoryCoverImage(@RequestParam("coverimage")MultipartFile image,@PathVariable String categoryId) throws IOException {
        String uploadFileName = fileService.uploadFile(image,coverimagePath );
        CatergoryDto catergoryDto = categoryService.get(categoryId);
        catergoryDto.setCoverImage(uploadFileName);
        CatergoryDto update = categoryService.update(catergoryDto, categoryId);
        ImageResponse response=ImageResponse.builder().imageName(uploadFileName).message("cover image uploaded successfully !!").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping("/coverimage/{categoryId}")
    public void serveCategoryCoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CatergoryDto catergoryDto = categoryService.get(categoryId);
        String coverImageName = catergoryDto.getCoverImage();
        InputStream resource = fileService.getResource(coverimagePath, coverImageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
    //create product with category
    //create product with category
//create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto
    ) {
        ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}/products/{productId}")

    public ResponseEntity<ProductDto> updateCategoryOfproduct( @PathVariable("categoryId") String categoryId,@PathVariable("productId") String productId){

        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);

    }
   // get product of Category

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsOfCategory( @PathVariable("categoryId") String categoryId,
                                                                                  @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                                                  @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                                                                  @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
                                                                                  @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse = productService.getAllOfCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);

    }

}
