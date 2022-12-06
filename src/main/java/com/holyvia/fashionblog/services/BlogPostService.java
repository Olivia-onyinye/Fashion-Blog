package com.holyvia.fashionblog.services;

import com.holyvia.fashionblog.dtos.BPostResponseDto;
import com.holyvia.fashionblog.dtos.BlogPostCreateDto;
import com.holyvia.fashionblog.dtos.BlogPostUpdateDto;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.pagination_criteria.BlogPostPages;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogPostService {

    BlogPost createPost(BlogPostCreateDto blogPostCreateDto, Long category_id);
    Page<BlogPost> getAllPosts(BlogPostPages blogPostPages);
    void deletePost(Long post_id);
    BlogPost updateBlogPost(BlogPostUpdateDto blogPostUpdateDto, Long post_id);
    BPostResponseDto viewBlogPost(Long post_id);
    List<BPostResponseDto> ViewPostByCategory(Long category_id);
}