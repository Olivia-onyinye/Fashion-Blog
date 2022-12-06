package com.holyvia.fashionblog.repositories;

import com.holyvia.fashionblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long > {
    @Query(value = "SELECT * FROM comment WHERE post_id =? AND comment_id = ?", nativeQuery = true)
    Comment findCommentByCommentIdAndPostId(Long post_id, Long comment_id);
}
