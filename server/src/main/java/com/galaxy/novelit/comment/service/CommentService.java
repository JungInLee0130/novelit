package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.response.CommentInfoResponse;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.request.CommentDeleteRequest;
import com.galaxy.novelit.comment.request.CommentUpdateRequest;
import com.galaxy.novelit.comment.entity.Comment;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    @Transactional
    public void addComment(CommentAddRequest commentAddRequest, String userUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(commentAddRequest.spaceUUID())
                .orElseGet(null);

        if (Objects.isNull(comment)) {
            createFirstComment(commentAddRequest, userUUID);
        }
        else {
            putAnotherComment(comment, commentAddRequest, userUUID);
        }
    }

    private void createFirstComment(CommentAddRequest commentAddRequest, String userUUID) {
        Comment firstComment = Comment.createComment(commentAddRequest, userUUID);
        commentRepository.save(firstComment);
    }

    private void putAnotherComment(Comment comment, CommentAddRequest commentAddRequest, String userUUID) {
        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        CommentInfo commentInfo = CommentInfo.createCommentInfo(commentAddRequest, userUUID);
        commentInfoList.add(commentInfo);

        comment.updateCommentInfoList(commentInfoList);

        commentRepository.save(comment);
    }


    @Transactional(readOnly = true)
    public List<CommentInfoResponse> getAllCommentInfos(String spaceUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfos = comment.getCommentInfoList();

        return CommentInfoResponse.infoListToDtoList(commentInfos);
    }



    @Transactional
    public void updateComment(CommentUpdateRequest commentUpdateRequest, String userUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(commentUpdateRequest.spaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        comment.updateCommentInfoList(commentUpdateRequest, userUUID);
    }

    public CommentInfo getCommentInfo(String spaceUUID, String commentUUID) {
        Comment comment = commentRepository.findCommentBySpaceUUID(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfos = comment.getCommentInfoList();

        for (CommentInfo commentInfo : commentInfos) {
            if (commentInfo.getCommentUUID().equals(commentUUID)) {
                return commentInfo;
            }
        }
        throw new CustomException(ErrorCode.NO_SUCH_COMMNET_INFO);
    }


    @Transactional
    public void deleteComment(CommentDeleteRequest commentDeleteRequest, String userUUID) {
        // 코멘트 서치
        Comment comment = commentRepository.findCommentBySpaceUUID(
                commentDeleteRequest.spaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        // 세부 코멘트 서치
        for (CommentInfo info :commentInfoList) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (info.getCommentUUID().equals(commentDeleteRequest.commentUUID())
                && info.getUserUUID().equals(userUUID)) {
                commentInfoList.remove(info);
                comment.updateCommentInfoList(commentInfoList);
                commentRepository.save(comment);
                return;
            }
        }

        throw new CustomException(ErrorCode.NO_SUCH_COMMENT_UUID, "method : deleteComment. 해당하는 코멘트 UUID가 없습니다!");
    }
}
