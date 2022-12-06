package com.holyvia.fashionblog.services.serviceImpl;

import com.holyvia.fashionblog.dtos.CategoryDto;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.*;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.pagination_criteria.CategoryPages;
import com.holyvia.fashionblog.repositories.CategoryRepo;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final HttpSession session;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only admins can upload post");
        }
        if (categoryRepo.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new AlreadyExistException("Please create another category with a different name");
        }
        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .build();
        categoryRepo.save(category);
        return category;
    }

    @Override
    public CategoryDto viewCategoryById(Long category_id) {
        Category category = categoryRepo.findById(category_id)
                .orElseThrow(() -> new CategoryNotFoundException("Put in the correct Id"));
        CategoryDto cd = CategoryDto.builder()
                .categoryName(category.getCategoryName())
                .build();
        return cd;
    }

    @Override
    public void deleteCategory(Long category_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only admins can delete a post");
        }
        Category category = categoryRepo.findById(category_id).orElseThrow(() ->
                new CategoryNotFoundException("Put in the correct Id"));
        categoryRepo.delete(category);
    }

    @Override
    public Page<Category> viewAllCategories(CategoryPages categoryPages) {
        List<Category> categoryList = categoryRepo.findAll();
        if(categoryList.isEmpty()){
            throw new EmptyListException("There are no categories yet", "Kindly create categories");
        }
       Sort sort = Sort.by(categoryPages.getSortDirection(), categoryPages.getSortBy());
        Pageable pageable = PageRequest.of(categoryPages.getPageNumber(), categoryPages.getPageSize(), sort);
        return categoryRepo.findAll(pageable);
    }
    @Override
    public Category editCategory(CategoryDto categoryDto, Long category_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only admins can edit a post");
        }
        Category category = categoryRepo.findById(category_id).orElseThrow(() ->
                new CategoryNotFoundException("Put in the correct Id"));
        category.setCategoryName(categoryDto.getCategoryName());
        return categoryRepo.save(category);
    }
}