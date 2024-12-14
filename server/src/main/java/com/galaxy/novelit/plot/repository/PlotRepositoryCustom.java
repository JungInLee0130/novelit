package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;

import java.util.List;
import java.util.Optional;

public interface PlotRepositoryCustom {
    Optional<List<Plot>> findByKeyword(String workspaceUuid, String keyword);
}
