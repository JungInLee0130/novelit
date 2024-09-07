package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class PlotServiceImpl implements PlotService{

    private final PlotRepository plotRepository;

    @Override
    @Transactional(readOnly = true)
    public PlotListResponseDto getPlotList(String workspaceUuid) {
        List<Plot> plotEntities = plotRepository.findAllByWorkspaceUuid(workspaceUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));
        return PlotListResponseDto.entityToDto(plotEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public PlotListResponseDto getPlotListByKeyword(String workspaceUuid, String keyword) {
        List<Plot> plotEntities = plotRepository.findByKeyword(workspaceUuid, keyword)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));

        return PlotListResponseDto.entityToDto(plotEntities);
    }

    @Override
    @Transactional
    public void createPlot(PlotCreateRequestDto dto) {
        String plotUuid = UUID.randomUUID().toString();

        plotRepository.save(Plot.create(plotUuid, dto));
    }

    @Override
    @Transactional(readOnly = true)
    public PlotDetailsResponseDto getPlotDetails(String plotUuid) {

        Plot plot = plotRepository.findByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "세부정보를 가져올 플롯이 없습니다."));

        return PlotDetailsResponseDto.toDto(plot);
    }

    @Override
    @Transactional
    public void savePlot(PlotSaveRequestDto dto) {
        Plot plot = plotRepository.findByPlotUuid(dto.getPlotUuid())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "업데이트 할 플롯이 없습니다."));

        if (dto.getPlotTitle() != null) {
            plot.updatePlotTitle(dto.getPlotTitle());
        }

        if (dto.getStory() != null) {
            plot.updateStory(dto.getStory());
        }

        if (dto.getBeginning() != null) {
            plot.updateBeginning(dto.getBeginning());
        }

        if (dto.getRising() != null) {
            plot.updateRising(dto.getRising());
        }

        if (dto.getCrisis() != null) {
            plot.updateCrisis(dto.getCrisis());
        }

        if (dto.getClimax() != null) {
            plot.updateClimax(dto.getClimax());
        }

        if (dto.getEnding() != null) {
            plot.updateEnding(dto.getEnding());
        }
    }

    @Override
    @Transactional
    public void deletePlot(String plotUuid) {
        plotRepository.deletePlotEntitiesByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "삭제할 플롯이 없습니다."));
    }
}
