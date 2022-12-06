package com.holyvia.fashionblog.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogPostUpdateDto {
    private String title;
    private String description;
    private String imageUrl;
}
