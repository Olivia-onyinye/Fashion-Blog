package com.holyvia.fashionblog.repositories;

import com.holyvia.fashionblog.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long > {
}
