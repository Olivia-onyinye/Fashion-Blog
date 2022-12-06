package com.holyvia.fashionblog.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class BPostResponseDto {
    private Long post_id;
    private String title;
    private String description;
    private String imageUrl;
}
