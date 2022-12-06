package com.holyvia.fashionblog.repositories;

import com.holyvia.fashionblog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long > {

    boolean existsByCategoryName(String categoryName);

}
