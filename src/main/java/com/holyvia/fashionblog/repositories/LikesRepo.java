package com.holyvia.fashionblog.repositories;

import com.holyvia.fashionblog.models.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Long > {
}
