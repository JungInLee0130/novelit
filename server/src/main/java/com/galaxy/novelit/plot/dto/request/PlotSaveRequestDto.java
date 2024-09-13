package com.galaxy.novelit.plot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotSaveRequestDto {
    @NotBlank
    private String plotUuid;
    @NotBlank(message = "제목을 입력하세요.")
    private String plotTitle;

    private String story;
    private String beginning;
    private String rising;
    private String crisis;
    private String climax;
    private String ending;
}
