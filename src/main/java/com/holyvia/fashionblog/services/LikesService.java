package com.holyvia.fashionblog.services;

public interface LikesService {
     void likeAPost(Long post_id);
    Integer countNumberOfLikes(Long post_id);
    void unLikeAPost(Long post_id, Long like_id);

}
