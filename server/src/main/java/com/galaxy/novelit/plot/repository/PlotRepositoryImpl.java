package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.entity.QPlot;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.galaxy.novelit.plot.entity.QPlot.plot;

@Slf4j
@RequiredArgsConstructor
public class PlotRepositoryImpl implements PlotRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Plot>> findByKeyword(String workspaceUuid, String keyword) {

        String pattern = keyword + "%"; // 2. 젤 앞에
        String pattern2 = "%" + keyword + "%"; // 2. 중간

        
        List<Plot> keywordPlots = queryFactory
            .selectFrom(plot)
            .where(
                    plot.plotTitle.like(pattern)
                    .and(plot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        List<Plot> keywordPlots2 = queryFactory
                .selectFrom(plot)
                .where(
                        plot.plotTitle.like(pattern2)
                                .and(plot.workspaceUuid.eq(workspaceUuid))
                )
                .fetch();

        // plot들 모두 담음 -> set으로 중복없앰?
        LinkedHashSet<Plot> keywordsSet = new LinkedHashSet<>();

        keywordPlots.stream().forEach(plot1 -> {
            keywordsSet.add(plot1);
        });
        keywordPlots2.stream().forEach(plot1 -> {
            keywordsSet.add(plot1);
        });

        return Optional.ofNullable(keywordsSet.stream().toList());
    }
}
