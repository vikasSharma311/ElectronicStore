package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CatergoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Value("${category.image.path}")
    private String coverimagePath;

    @Override
    public CatergoryDto create(CatergoryDto catergoryDto) {
        String categoryId = UUID.randomUUID().toString();
        catergoryDto.setCategoryId(categoryId);
        Category category = mapper.map(catergoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CatergoryDto.class);
    }

    @Override
    public CatergoryDto update(CatergoryDto catergoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
        category.setTitle(catergoryDto.getTitle());
        category.setDescription(catergoryDto.getDescription());
        category.setCoverImage(catergoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(category, CatergoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
        String coverImage = category.getCoverImage();
        String fullpath=coverimagePath+coverImage;
        try {
            Path path = Paths.get(fullpath);
            if (Files.exists(path) && Files.size(path) > 0) {
                Files.delete(path);
            } else {
                logger.info("User image not found in folder or file size is zero");
            }
        } catch (NoSuchFileException ex){
        logger.info("cover image not found in folder");
        ex.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CatergoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CatergoryDto> pageableResponse = Helper.getPageableResponse(page, CatergoryDto.class);
        return pageableResponse;
    }

    @Override
    public CatergoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        return mapper.map(category, CatergoryDto.class);
    }


}
