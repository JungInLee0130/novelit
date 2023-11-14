package com.galaxy.novelit.notification.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private String type;
    private String subscriberUUID;
    private String notificationUUID;
    private String notificationContent;


    public static NotificationResponseDto createAlarmComment(String commentNickname, String id){
        UUID uuid = UUID.randomUUID();

        String notiUUID = uuid.toString();

        return NotificationResponseDto.builder()
            .type("alertComment")
            .subscriberUUID(id)
            .notificationUUID(notiUUID)
            .notificationContent(commentNickname + " 님이 댓글을 남겼습니다.")
            .build();
    }
}