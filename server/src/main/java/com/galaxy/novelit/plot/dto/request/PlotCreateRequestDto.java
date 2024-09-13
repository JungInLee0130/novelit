package com.galaxy.novelit.plot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlotCreateRequestDto {

    @NotBlank
    private String workspaceUuid; // 파일 UUID

    @NotBlank(message = "제목을 입력하세요.")
    private String plotTitle; // 플롯 제목

    private String story;

    private String beginning;
    private String rising;
    private String crisis;
    private String climax;
    private String ending;
}
