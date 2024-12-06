package com.galaxy.novelit.comment.domain;

import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.common.utils.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfo extends BaseTimeEntity {
    private String commentUUID;
    private String commentContent;
    private String commentNickname;
    private String userUUID;

    public CommentInfo(String commentUUID, String commentContent, String commentNickname, String userUUID) {
        this.commentUUID = commentUUID;
        this.commentContent = commentContent;
        this.commentNickname = commentNickname;
        this.userUUID = userUUID;
    }

    @Builder
    public CommentInfo(String commentContent, String commentNickname, String userUUID) {
        this.commentUUID = UUID.randomUUID().toString();
        this.commentContent = commentContent;
        this.commentNickname = commentNickname;
        this.userUUID = userUUID;
    }

    public static CommentInfo createCommentInfo(CommentAddRequest commentAddRequest, String userUUID) {
        return CommentInfo.builder()
                .commentContent(commentAddRequest.commentContent())
                .commentNickname(commentAddRequest.commentNickname())
                .userUUID(userUUID)
                .build();
    }

    public void updateCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
