package com.galaxy.novelit.plot.response;

import com.galaxy.novelit.plot.entity.Plot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotDetailResponse {
    private String plotTitle;
    private String story;
    private String beginning;
    private String rising;
    private String crisis;
    private String climax;
    private String ending;

    // entity -> dto
    public static PlotDetailResponse toDto(Plot plot) {
        return PlotDetailResponse.builder()
            .plotTitle(plot.getPlotTitle())
            .story(plot.getStory())
            .beginning(plot.getBeginning())
            .rising(plot.getRising())
            .crisis(plot.getCrisis())
            .climax(plot.getClimax())
            .ending(plot.getEnding())
            .build();
    }
}
