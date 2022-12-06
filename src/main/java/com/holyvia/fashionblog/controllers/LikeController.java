package com.holyvia.fashionblog.controllers;

import com.holyvia.fashionblog.services.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikesService likeService;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> likeAPost (@PathVariable("post_id") Long post_id){
        likeService.likeAPost(post_id);
        return new ResponseEntity<>("post liked", HttpStatus.CREATED);
    }

    @GetMapping("/count/{post_id}")
    public ResponseEntity<Integer> likesCounter (@PathVariable("post_id") Long post_id){
        Integer count = likeService.countNumberOfLikes(post_id);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    @DeleteMapping("/{post_id}/{like_id}")
    public  ResponseEntity<String> unlikeAPost(@PathVariable("post_id") Long post_id, @PathVariable("like_id") Long like_id){
        likeService.unLikeAPost(post_id, like_id);
        return new ResponseEntity<>("Post Unliked", HttpStatus.OK);
    }
}
