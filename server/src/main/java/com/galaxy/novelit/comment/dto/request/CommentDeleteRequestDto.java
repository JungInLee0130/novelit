package com.galaxy.novelit.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteRequestDto {

    @NotBlank
    private String spaceUUID;
    
    @NotBlank
    private String commentUUID;
}
