package com.galaxy.novelit.comment.controller;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody @Valid CommentAddRequestDto commentAddRequestDto
        , String username){
        String publisherUUID = username;

        commentService.addComment(commentAddRequestDto, publisherUUID);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentInfoDto>> getAllComments(@RequestParam String spaceUUID) {
        return ResponseEntity.ok(commentService.getAllComments(spaceUUID));
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody @Valid CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateComment(commentUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody @Valid CommentDeleteRequestDto commentDeleteRequestDto) {
        commentService.deleteComment(commentDeleteRequestDto);
        return ResponseEntity.ok().build();
    }
}
