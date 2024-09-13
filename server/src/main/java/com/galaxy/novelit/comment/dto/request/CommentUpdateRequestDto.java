package com.galaxy.novelit.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequestDto {

    @NotBlank
    private String spaceUUID; // 공간 ID

    @NotBlank
    private String commentUUID; // 코멘트 UUID

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(min = 1, max = 200, message = "1~200자이내 여야합니다.")
    private String commentContent; // 코멘트 내용
}
