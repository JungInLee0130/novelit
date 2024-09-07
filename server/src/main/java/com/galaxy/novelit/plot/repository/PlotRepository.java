package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository <Plot, Long> , PlotRepositoryCustom{

    Optional<List<Plot>> findAllByWorkspaceUuid(String workspaceUuid);
    Optional<List<Plot>> findByWorkspaceUuidAndPlotTitleContaining(String workspaceUuid, String keyword);
    Optional<Plot> findByPlotUuid(String plotUuid);
    Optional<Plot> deletePlotEntitiesByPlotUuid(String plotUuid);
}
