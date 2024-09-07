package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.entity.QPlot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PlotRepositoryImpl implements PlotRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Plot>> findByKeyword(String workspaceUuid, String keyword) {

        String pattern = keyword + "%";
        String pattern2 = "%" + keyword + "%";

        QPlot qPlot = QPlot.plot;

        List<Plot> list1 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.eq(keyword)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        List<Plot> list2 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.like(pattern)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        List<Plot> list3 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.like(pattern2)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        LinkedHashMap<String, Plot> plotEntityHashMap = new LinkedHashMap<>();

        List<Plot> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        result.addAll(list3);

        for (Plot plot : result) {
            plotEntityHashMap.put(plot.getPlotUuid(), plot);
        }

        List<Plot> valueList = new ArrayList<>(plotEntityHashMap.values());


        return Optional.ofNullable(valueList);
    }
}
