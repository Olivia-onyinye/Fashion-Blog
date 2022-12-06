package com.holyvia.fashionblog.controllers;

import com.holyvia.fashionblog.dtos.CommentDto;
import com.holyvia.fashionblog.models.Comment;
import com.holyvia.fashionblog.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comment")

public class CommentController {
    private final CommentService commentService ;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> toMakeAComment (@Valid @RequestBody CommentDto commentDto,
                                                   @PathVariable("post_id") Long post_id){
    commentService.createComment(commentDto, post_id);
    return new ResponseEntity<>("Comment has been recorded", HttpStatus.CREATED);
    }
    @GetMapping("/{comment_id}")
    public ResponseEntity<CommentDto> showACommentById(@PathVariable("comment_id") Long comment_id){
        return new ResponseEntity<>(commentService.viewCommentById(comment_id), HttpStatus.OK);
    }

     @GetMapping("/count/{post_id}")
    public ResponseEntity<Integer> viewTheTotalCountOfComments(@PathVariable("post_id") Long post_id){
    Integer count = commentService.countNumberOfComments(post_id);
          return new ResponseEntity<>(count, HttpStatus.OK);
    }
    @GetMapping("/view/{post_id}")
    public ResponseEntity<List<Comment>> viewAllCommentsOfAPost(@PathVariable("post_id") Long post_id){
        return new ResponseEntity<>(commentService.getAllCommentsOnAPost(post_id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{post_id}/{comment_id}")
    public ResponseEntity<String> deleteAComment(@PathVariable("post_id") Long post_id,
                                                 @PathVariable("comment_id") Long comment_id){
        commentService.deleteCommentById(post_id, comment_id);
        return new ResponseEntity<>("Comment Deleted", HttpStatus.NO_CONTENT);
    }
}
