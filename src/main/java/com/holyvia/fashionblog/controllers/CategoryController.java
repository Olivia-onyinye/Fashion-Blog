package com.holyvia.fashionblog.controllers;


import com.holyvia.fashionblog.dtos.CategoryDto;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.pagination_criteria.CategoryPages;
import com.holyvia.fashionblog.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final HttpSession session;


    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>("Category created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDto> viewCategoryById(@PathVariable("category_id") Long category_id){
        return new ResponseEntity<>(categoryService.viewCategoryById(category_id), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Page<Category>> viewAllCategories(CategoryPages categoryPages){
        return new ResponseEntity<>(categoryService.viewAllCategories(categoryPages), HttpStatus.OK);
    }

    @PatchMapping("/{category_id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("category_id") Long category_id,
                                                  @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.editCategory(categoryDto, category_id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category_id") Long category_id){
        categoryService.deleteCategory(category_id);
        return new ResponseEntity<>("category successfully deleted", HttpStatus.NO_CONTENT);
    }
}
