package com.holyvia.fashionblog.services.serviceImpl;

import com.holyvia.fashionblog.dtos.CommentDto;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.PostNotFoundException;
import com.holyvia.fashionblog.exceptions.ResourceNotFoundException;
import com.holyvia.fashionblog.exceptions.UnauthorizedOperationException;
import com.holyvia.fashionblog.models.BlogPost;
import com.holyvia.fashionblog.models.Comment;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.repositories.BlogPostRepo;
import com.holyvia.fashionblog.repositories.CommentRepo;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;
    private  final HttpSession session;


    @Override
    public Comment createComment(CommentDto commentDto, Long post_id) {
        BlogPost blogPost1 = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        Comment comment = Comment.builder()
                .commenterName(commentDto.getCommenterName())
                .email(commentDto.getEmail())
                .body(commentDto.getBody())
                .blogPost(blogPost1)
                .build();
        commentRepo.save(comment);
        return comment;
    }

    @Override
    public CommentDto viewCommentById(Long comment_id) {
        Comment comment = commentRepo.findById(comment_id)
                .orElseThrow(() ->new ResourceNotFoundException("This comment is not found"));
        return CommentDto.builder()
                .commenterName(comment.getCommenterName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    @Override
    public List<Comment> getAllCommentsOnAPost(Long post_id) {
        BlogPost blogPost = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        return blogPost.getComments();
    }

    @Override
    public Integer countNumberOfComments(Long post_id) {
        BlogPost blogPost = blogPostRepo.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException("This post was not found"));
        return blogPost.getComments().size();
    }

    @Override
    public void deleteCommentById(Long post_id, Long comment_id) {
        Long user_id = (Long) session.getAttribute("user_id");
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist, kindly register"));
        if (!user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only admins can upload post");
        }
        Comment comment = commentRepo.findCommentByCommentIdAndPostId(post_id, comment_id);
        commentRepo.delete(comment);
    }
}