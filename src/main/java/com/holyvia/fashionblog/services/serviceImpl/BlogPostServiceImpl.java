package com.holyvia.fashionblog.services.serviceImpl;

import com.holyvia.fashionblog.dtos.BPostResponseDto;
import com.holyvia.fashionblog.dtos.BlogPostCreateDto;
import com.holyvia.fashionblog.dtos.BlogPostUpdateDto;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.*;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Category;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.pagination_criteria.BlogPostPages;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.CategoryRepo;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.BlogPostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private  final HttpSession session;

    @Override
    public BlogPost createPost(BlogPostCreateDto blogPostCreateDto, Long category_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only admins can upload post");
        }
        Category category = categoryRepo.findById(category_id)
                .orElseThrow(() -> new CategoryNotFoundException("Put in the correct Id"));
        BlogPost BPost = BlogPost.builder()
                .title(blogPostCreateDto.getTitle())
                .description(blogPostCreateDto.getDescription())
                .imageUrl(blogPostCreateDto.getImageUrl())
                .comments(new ArrayList<>())
                .postAuthor(user)
                .category(category)
                .build();
        blogPostRepo.save(BPost);
        return BPost;
    }

    @Override
    public Page<BlogPost> getAllPosts(BlogPostPages blogPostPages) {
        List<BlogPost> blogPostList = blogPostRepo.findAll();
        if(blogPostList.isEmpty()){
            throw new EmptyListException("There are no categories yet", "Kindly create categories");
        }
        Sort sort = Sort.by(blogPostPages.getSortDirection(), blogPostPages.getSortBy());
        Pageable pageable = PageRequest.of(blogPostPages.getPageNumber(), blogPostPages.getPageSize(), sort);
        return blogPostRepo.findAll(pageable);
    }

    @Override
    public void deletePost(Long post_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only admins can upload post");
        }
        BlogPost blogPost = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        blogPostRepo.delete(blogPost);
    }

    @Override
    public BlogPost updateBlogPost(BlogPostUpdateDto blogPostUpdateDto, Long post_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only admins can upload post");
        }
        BlogPost blogPost = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        blogPost.setTitle(blogPostUpdateDto.getTitle());
        blogPost.setDescription(blogPostUpdateDto.getDescription());
        blogPost.setImageUrl(blogPostUpdateDto.getImageUrl());
        return blogPostRepo.save(blogPost);
    }
    @Override
    public BPostResponseDto viewBlogPost(Long post_id) {
       BlogPost  blogPost =  blogPostRepo.findById(post_id)
                    .orElseThrow(() -> new PostNotFoundException("This post was not found"));
       return BPostResponseDto.builder()
                .post_id(blogPost.getPost_id())
                .title(blogPost.getTitle())
                .description(blogPost.getDescription())
                .imageUrl(blogPost.getImageUrl())
                .build();
    }

    @Override
    public List<BPostResponseDto> ViewPostByCategory(Long category_id) {
        Category category = categoryRepo.findById(category_id)
                .orElseThrow(() -> new CategoryNotFoundException("Put in the correct Id"));
        List<BlogPost> blogPosts = category.getBlogPosts();
        List<BPostResponseDto> bPostResponseDtoList = new ArrayList<>();
        blogPosts.forEach( blogPost ->{
            BPostResponseDto  bprd = BPostResponseDto.builder()
                    .post_id(blogPost.getPost_id())
                    .title(blogPost.getTitle())
                    .description(blogPost.getDescription())
                    .imageUrl(blogPost.getImageUrl())
                    .build();
            bPostResponseDtoList.add(bprd);
        });
        return bPostResponseDtoList;
    }
}
