package com.galaxy.novelit.comment.entity;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    private String _id;
    private String spaceUUID;
    private String directoryUUID;
    private List<CommentInfo> commentInfoList;

    public Comment(String _id, String spaceUUID, String directoryUUID, List<CommentInfo> commentInfoList) {
        this._id = _id;
        this.spaceUUID = spaceUUID;
        this.directoryUUID = directoryUUID;
        this.commentInfoList = commentInfoList;
    }

    @Builder
    public Comment(String spaceUUID, String directoryUUID, List<CommentInfo> commentInfoList) {
        this.spaceUUID = spaceUUID;
        this.directoryUUID = directoryUUID;
        this.commentInfoList = commentInfoList;
    }

    public static Comment createComment(CommentAddRequest commentAddRequest, String userUUID) {
        List<CommentInfo> commentInfoList = new ArrayList<>();

        CommentInfo commentInfo = CommentInfo.builder()
                .commentContent(commentAddRequest.commentContent())
                .commentNickname(commentAddRequest.commentNickname())
                .commentUserUUID(userUUID)
                .build();


        commentInfoList.add(commentInfo);


        return Comment.builder()
            .spaceUUID(commentAddRequest.spaceUUID())
            .directoryUUID(commentAddRequest.directoryUUID())
            .commentInfoList(commentInfoList)
            .build();
    }

    public void updateCommentInfoList(List<CommentInfo> list){
        this.commentInfoList = list;
    }

    public void updateCommentInfoList(CommentUpdateRequest commentUpdateRequest, String userUUID) {
        validateCommentInfoList(this.commentInfoList);

        for (CommentInfo info : commentInfoList) {
            String curInfoUUID = info.getCommentUUID();
            String updateCommentUUID = commentUpdateRequest.commentUUID();

            if (curInfoUUID.equals(updateCommentUUID)) {
                validateSameCommentWriter(info.getCommentUserUUID(), userUUID);
                info.updateCommentContent(commentUpdateRequest.commentContent());
                break;
            }
        }
    }

    private void validateCommentInfoList(List<CommentInfo> commentInfoList) {
        if (Objects.isNull(commentInfoList)){
            throw new CustomException(ErrorCode.NOT_FOUND, "댓글목록이 없습니다.");
        }
    }

    private void validateSameCommentWriter(String commentUserUUID, String loginUUID) {
        if (!commentUserUUID.equals(loginUUID)) { // 일치
            throw new CustomException(ErrorCode.NOT_COMMENT_WRITER);
        }
    }


}
