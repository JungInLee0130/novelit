package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String userUUID;

    public static CommentInfo create(CommentAddRequestDto commentAddRequestDto, String userUUID) {
        UUID uuid = UUID.randomUUID();

        String str = uuid.toString();

        return CommentInfo.builder()
            .commentUUID(str)
            .commentContent(commentAddRequestDto.getCommentContent())
            .commentNickname(commentAddRequestDto.getCommentNickname())
            .userUUID(userUUID)
            .build();
    }

    public void updateCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "commentUUID='" + commentUUID + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentNickname='" + commentNickname + '\'' +
                ", userUUID='" + userUUID + '\'' +
                '}';
    }
}
