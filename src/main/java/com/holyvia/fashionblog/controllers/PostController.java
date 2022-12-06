package com.holyvia.fashionblog.controllers;

import com.holyvia.fashionblog.dtos.BPostResponseDto;
import com.holyvia.fashionblog.dtos.BlogPostCreateDto;
import com.holyvia.fashionblog.dtos.BlogPostUpdateDto;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.pagination_criteria.BlogPostPages;
import com.holyvia.fashionblog.services.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/blogPost")
public class PostController {
    private final BlogPostService blogPostService;

    @PostMapping("/upload/{category_id}")
    public ResponseEntity<String> uploadBlogPost(@Valid @RequestBody BlogPostCreateDto blogPostCreateDto,
                                                 @PathVariable("category_id") Long category_id){
        blogPostService.createPost(blogPostCreateDto, category_id);
        return new ResponseEntity<>("Your post has been uploaded successfully", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<BlogPost>> viewAllPosts (BlogPostPages blogPostPages){
        return new ResponseEntity<>(blogPostService.getAllPosts(blogPostPages), HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<BPostResponseDto> viewABlogPostById(@PathVariable ("post_id") Long post_id){
        return new ResponseEntity<>(blogPostService.viewBlogPost(post_id), HttpStatus.OK);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<BlogPost> modifyBlogPost(@Valid @RequestBody BlogPostUpdateDto blogPostUpdateDto,
                                                   @PathVariable("post_id") Long post_id){
        return new ResponseEntity<>(blogPostService.updateBlogPost(blogPostUpdateDto, post_id), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> deleteAPost(@PathVariable ("post_id") Long post_id){
        blogPostService.deletePost(post_id);
        return new ResponseEntity<>("BlogPost deleted successfully", HttpStatus.NO_CONTENT);
    }

}
