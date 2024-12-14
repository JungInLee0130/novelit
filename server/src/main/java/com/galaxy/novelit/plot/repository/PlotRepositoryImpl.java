package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.entity.QPlot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
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

        // 1. 아예 일치
        List<Plot> list1 = queryFactory
            .selectFrom(plot)
            .where(
                    plot.plotTitle.eq(keyword)
                    .and(plot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        // 2. 젤 앞에
        List<Plot> list2 = queryFactory
            .selectFrom(plot)
            .where(
                    plot.plotTitle.like(pattern)
                    .and(plot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        // 3. 중간에
        List<Plot> list3 = queryFactory
            .selectFrom(plot)
            .where(
                    plot.plotTitle.like(pattern2)
                    .and(plot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        // 순서보장
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
