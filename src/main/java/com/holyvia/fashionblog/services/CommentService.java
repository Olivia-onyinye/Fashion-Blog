package com.holyvia.fashionblog.services;

import com.holyvia.fashionblog.dtos.CommentDto;
import com.holyvia.fashionblog.models.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment (CommentDto commentDto, Long post_id);
    CommentDto viewCommentById(Long comment_id);
    List<Comment> getAllCommentsOnAPost(Long post_id);
    Integer countNumberOfComments(Long post_id);
   void deleteCommentById(Long post_id, Long comment_id);
}
