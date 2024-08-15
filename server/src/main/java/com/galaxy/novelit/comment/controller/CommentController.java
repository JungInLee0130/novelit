package com.galaxy.novelit.comment.controller;

import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentDeleteRequestDto;
import com.galaxy.novelit.comment.dto.request.CommentUpdateRequestDto;
import com.galaxy.novelit.comment.service.CommentService;
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

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentAddRequestDto commentAddRequestDto
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
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    , String username) {
        commentService.updateComment(commentUpdateRequestDto, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDeleteRequestDto commentDeleteRequestDto
    , String username) {
        commentService.deleteComment(commentDeleteRequestDto, username);
        return ResponseEntity.ok().build();
    }
}
