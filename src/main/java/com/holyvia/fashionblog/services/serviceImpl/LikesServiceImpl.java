package com.holyvia.fashionblog.services.serviceImpl;

import com.holyvia.fashionblog.exceptions.PostNotFoundException;
import com.holyvia.fashionblog.exceptions.ResourceNotFoundException;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Likes;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.CommentRepo;
import com.holyvia.fashionblog.repositories.LikesRepo;
import com.holyvia.fashionblog.services.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepo likesRepo;
    private final CommentRepo commentRepo;
    private final BlogPostRepo blogPostRepo;

    @Override
    public void likeAPost(Long post_id) {
        BlogPost blogPost1 = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        Likes likes = Likes.builder()
                .blogPost(blogPost1)
                .build();
        likesRepo.save(likes);
    }

    @Override
    public Integer countNumberOfLikes(Long post_id) {
        BlogPost blogPost1 = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        return blogPost1.getLikesList().size();
    }

    @Override
    public void unLikeAPost(Long post_id, Long like_id) {
        BlogPost blogPost1 = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
       Likes likes = likesRepo.findById(like_id).orElseThrow(() -> new ResourceNotFoundException("This like is not found"));
        likesRepo.delete(likes);}
}