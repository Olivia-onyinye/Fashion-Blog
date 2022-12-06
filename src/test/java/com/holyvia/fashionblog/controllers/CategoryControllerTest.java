package com.holyvia.fashionblog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holyvia.fashionblog.dtos.CategoryDto;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    void createCategory() {
        try {
            Category category = Category.builder()
                    .category_id(2L)
                    .categoryName("Look")
                    .blogPosts(new ArrayList<>())
                    .build();
            CategoryDto cdt = CategoryDto.builder()
                    .categoryName("Look")
                    .build();
            when(categoryService.createCategory(cdt)).thenReturn(category);
            String requestBody = mapper.writeValueAsString(cdt);
            mockMvc.perform(post("/api/v1/admin/category/create")
                                    .contentType("application/json").content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Category created Successfully"));
        }catch (Exception ce){
            ce.printStackTrace();
        }
    }

    @Test
    void viewCategoryById() {
        try {
            CategoryDto cdt = CategoryDto.builder()
                    .categoryName("Look")
                    .build();
            when(categoryService.viewCategoryById(anyLong())).thenReturn(cdt);
            mockMvc.perform(get("/api/v1/admin/category/{category_id}", 12L)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
    @Test
    void updateCategory() {
        try {
            Category category = Category.builder()
                    .category_id(12L)
                    .categoryName("Look")
                    .blogPosts(new ArrayList<>())
                    .build();
            CategoryDto cdt = CategoryDto.builder()
                    .categoryName("Look")
                    .build();
            when(categoryService.editCategory(cdt, 12L)).thenReturn(category);
            String requestBody = mapper.writeValueAsString(cdt);
            mockMvc.perform(patch("/api/v1/admin/category/{category_id}", 12L)
                            .contentType("application/json")
                            .contentType(requestBody));
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }

    @Test
    void deleteCategory() {

        try {
            mockMvc.perform(delete("/api/v1/admin/category/{category_id}", 12L)
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
}