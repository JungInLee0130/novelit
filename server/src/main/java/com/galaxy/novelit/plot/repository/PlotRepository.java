package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository <Plot, Long> , PlotRepositoryCustom{

    Optional<List<Plot>> findAllByWorkspaceUuid(String workspaceUuid);
    Optional<Plot> findPlotByPlotUuid(String plotUuid);
    Optional<Plot> deletePlotByPlotUuid(String plotUuid);
}
