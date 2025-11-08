package com.substring.quiz.functions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String id;

    private String title;

    private String description;

    private boolean active;
}
