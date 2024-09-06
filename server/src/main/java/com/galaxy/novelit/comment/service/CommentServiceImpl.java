package com.galaxy.novelit.comment.service;

import com.galaxy.novelit.comment.domain.Comment;
import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.repository.CommentRepository;
import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void addComment(CommentAddRequestDto commentAddRequestDto, String userUUID) {
        // 코멘트 찾기
        Optional<Comment> comment = commentRepository.findBySpaceUUID(
                commentAddRequestDto.getSpaceUUID());

        if (comment.isPresent()) {
            Comment comment1 = comment.get();
            List<CommentInfo> infoList = comment1.getCommentInfoList();
            infoList.add(CommentInfo.create(commentAddRequestDto, userUUID));
            comment1.updateCommentInfoList(infoList);
            commentRepository.save(comment.get());
        }
        else {
            commentRepository.save(Comment.create(commentAddRequestDto, userUUID));
        }
    }

    @Override
    public List<CommentInfoDto> getAllComments(String spaceUUID) {
        Comment comment = commentRepository.findBySpaceUUID(spaceUUID)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_COMMENT));

        return CommentInfoDto.infoListToDtoList(comment.getCommentInfoList());
    }



    @Override
    public void updateComment(CommentUpdateRequestDto updateResDto) {
        // 코멘트 서치
        Comment comment = commentRepository.findBySpaceUUID(updateResDto.getSpaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_SPACEUUID));
        
        // 성공시 : 코멘트 UUID로 가져오고 내용만 수정
        List<CommentInfo> infoList = comment.getCommentInfoList();

        // 세부 코멘트 서치 : 코멘트 UUID 확인
        for (CommentInfo info :infoList) {
            if (info.getCommentUUID().equals(updateResDto.getCommentUUID())) {
                info.updateCommentContent(updateResDto.getCommentContent());
                comment.updateCommentInfoList(infoList);
                commentRepository.save(comment);
                return;
            }
        }

        throw new CustomException(ErrorCode.NO_SUCH_COMMENT_UUID, "NO_SUCH_commentInfoList. 해당하는 코멘트 UUID가 없습니다!");
    }

    @Override
    public void deleteComment(CommentDeleteRequestDto deleteReqDto) {
        // 코멘트 서치
        Comment comment = commentRepository.findBySpaceUUID(deleteReqDto.getSpaceUUID())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_SPACEUUID));

        List<CommentInfo> commentInfoList = comment.getCommentInfoList();

        // 세부 코멘트 서치
        for (CommentInfo info :commentInfoList) {
            // 소설가인 경우 : 로그인한 사람이랑 같음. 비밀번호없음
            if (info.getCommentUUID().equals(deleteReqDto.getCommentUUID())) {
                commentInfoList.remove(info);
                comment.updateCommentInfoList(commentInfoList);
                commentRepository.save(comment);
                return;
            }
        }

        throw new CustomException(ErrorCode.NO_SUCH_COMMENT_UUID, "NO_SUCH_COMMENTINFOLIST. 해당하는 코멘트 UUID가 없습니다!");
    }
}
