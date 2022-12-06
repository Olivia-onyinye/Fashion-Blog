package com.holyvia.fashionblog.services;

import com.holyvia.fashionblog.dtos.CategoryDto;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.pagination_criteria.CategoryPages;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    CategoryDto viewCategoryById(Long category_id);

    void deleteCategory(Long category_id);
    Category editCategory(CategoryDto categoryDto, Long category_id);
    Page<Category> viewAllCategories(CategoryPages categoryPages);
}
