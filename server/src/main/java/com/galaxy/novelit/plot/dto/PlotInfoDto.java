package com.galaxy.novelit.plot.dto;

import com.galaxy.novelit.plot.entity.Plot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotInfoDto {
    private String plotUuid;
    private String plotTitle;
    private String story;

    public static PlotInfoDto entityToDto(Plot entity) {
        return PlotInfoDto.builder()
            .plotUuid(entity.getPlotUuid())
            .plotTitle(entity.getPlotTitle())
            .story(entity.getStory())
            .build();
    }
}

