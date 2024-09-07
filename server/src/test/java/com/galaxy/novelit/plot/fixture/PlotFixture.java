package com.galaxy.novelit.plot.fixture;

import com.galaxy.novelit.plot.entity.Plot;

public enum PlotFixture {
    DEFAULT(
                    3L,
                    "asdf-asdf",
                    "asdf-asdf",
                    "해리포터 1장",
                    "ㅇㅇ",
                    "ㅇㅇ",
                    "ㅇㅇ",
                    "ㅇㅇ",
                    "ㅇㅇ",
                    "ㅇㅇ"
    );

    private final Long plotId;
    private final String workspaceUuid;
    private final String plotUuid;
    private final String plotTitle;
    private final String story;
    private final String beginning;
    private final String rising;
    private final String crisis;
    private final String climax;
    private final String ending;

    PlotFixture(Long plotId, String workspaceUuid, String plotUuid, String plotTitle, String story,
                String beginning, String rising, String crisis, String climax, String ending) {
        this.plotId = plotId;
        this.workspaceUuid = workspaceUuid;
        this.plotUuid = plotUuid;
        this.plotTitle = plotTitle;
        this.story = story;
        this.beginning = beginning;
        this.rising = rising;
        this.crisis = crisis;
        this.climax = climax;
        this.ending = ending;
    }

    public Plot getPlot(){
        return Plot.builder()
                .plotId(plotId)
                .workspaceUuid(workspaceUuid)
                .plotUuid(plotUuid)
                .plotTitle(plotTitle)
                .story(story)
                .beginning(beginning)
                .rising(rising)
                .crisis(crisis)
                .climax(climax)
                .ending(ending)
                .build();
    }
}
