package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository <Plot, Long> , PlotRepositoryCustom{

    Optional<List<Plot>> findAllByWorkspaceUuid(String workspaceUuid);
    Optional<List<Plot>> findByWorkspaceUuidAndPlotTitleContaining(String workspaceUuid, String keyword);
    Optional<Plot> findPlotEntityByPlotUuid(String plotUuid);
    Optional<Plot> deletePlotEntitiesByPlotUuid(String plotUuid);
}
