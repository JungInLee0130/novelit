package com.galaxy.novelit.comment.request;

import jakarta.validation.constraints.NotBlank;

public record CommentAddRequest(@NotBlank String spaceUUID,
                                @NotBlank String directoryUUID,
                                @NotBlank String commentContent,
                                @NotBlank String commentNickname,
                                @NotBlank String userUUID) {

}
