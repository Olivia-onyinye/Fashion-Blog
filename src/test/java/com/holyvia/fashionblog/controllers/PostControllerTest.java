//package com.holyvia.fashionblog.controllers;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.holyvia.fashionblog.dtos.BlogPostCreateDto;
//import com.holyvia.fashionblog.models.BlogPost;
//import com.holyvia.fashionblog.models.Category;
//import com.holyvia.fashionblog.services.BlogPostService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.http.RequestEntity.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = PostController.class)
//class PostControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Autowired
//    private PostController postController;
//
//    @MockBean
//    private BlogPostService blogPostService;
//
//    @Test
//    void uploadBlogPost() {
//        try{
//            Category category = Category.builder().category_id(2L).categoryName("Look").blogPosts(new ArrayList<>()).build();
//            BlogPost blogPost = BlogPost.builder().post_id(2L).title("Midnight").description("darkshade").imageUrl("https://example.org/example").category(category).build();
//           BlogPostCreateDto blogPostCreateDto = BlogPostCreateDto.builder().title("Gowns").description("For plus sized women").imageUrl("Gsjknnsbjskhswuhwkk").build();
//            when(blogPostService.createPost(blogPostCreateDto, 2L)).thenReturn(blogPost);
//            String cool = mapper.writeValueAsString(blogPostCreateDto);
//            mockMvc.perform((RequestBuilder) post("api/v1/admin/blogPost/upload/{category_id}"))
//                    .andExpect(content().contentType("application/json"))
//                    .andExpect(content().string(cool))
//                    .andExpect(status().isCreated())
//                    .andExpect(content().string("Your post has been uploaded successfully"));
//        } catch (Exception xe){
//            xe.printStackTrace();
//        }
//    }

//    @Test
//    void viewAllPosts() {
//    }
//
//    @Test
//    void viewABlogPostById() {
//    }
//
//    @Test
//    void modifyBlogPost() {
//    }
//
//    @Test
//    void deleteAPost() {
//    }
//}