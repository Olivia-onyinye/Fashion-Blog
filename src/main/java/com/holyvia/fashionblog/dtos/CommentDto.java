package com.holyvia.fashionblog.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private String commenterName;
    private String email;
    private String body;
}
