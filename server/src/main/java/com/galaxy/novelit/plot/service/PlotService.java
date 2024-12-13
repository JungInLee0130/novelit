package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.plot.request.PlotCreateRequest;
import com.galaxy.novelit.plot.request.PlotSaveRequest;
import com.galaxy.novelit.plot.response.PlotDetailResponse;
import com.galaxy.novelit.plot.response.PlotListResponse;
import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class PlotService {

    private final PlotRepository plotRepository;

    /*@Override
    public PlotListResponseDto getPlotList(PlotListRequestDto plotListRequestDto) {
        if (plotListRequestDto.getKeyword() == null) {
            List<PlotEntity> plotEntities = plotRepository.findAllByWorkspaceUuid(
                    plotListRequestDto.getWorkspaceUuid())
                .orElseThrow(() -> new NoSuchPlotException());

            return PlotListResponseDto.entityToDto(plotEntities);
        }
        else if (plotListRequestDto.getKeyword() != null){
            // 키워드가있을때 -> querydsl 검색
            *//*List<PlotEntity> plotEntities = plotRepository.findByWorkspaceUuidAndPlotTitleContaining(
                    plotListRequestDto.getWorkspaceUuid(),
                    plotListRequestDto.getKeyword())
                .orElseThrow(() -> new NoSuchPlotException());
            return PlotListResponseDto.entityToDto(plotEntities);*//*

        }
        return null;
    }*/

    @Transactional(readOnly = true)
    public PlotListResponse getPlotListByKeyword(String workspaceUuid, String keyword) {
        List<Plot> plots = plotRepository.findByKeyword(workspaceUuid, keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));

        return PlotListResponse.plotListResponse(plots);
    }


    @Transactional(readOnly = true)
    public PlotListResponse getPlotList(String workspaceUuid) {
        List<Plot> plots = plotRepository.findAllByWorkspaceUuid(workspaceUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));
        return PlotListResponse.plotListResponse(plots);
    }


    @Transactional
    public void createPlot(PlotCreateRequest dto) {
        UUID plotUuid = UUID.randomUUID();

        String plotString = plotUuid.toString();

        Plot plot = plotRepository.save(Plot.create(plotString, dto));

        if (plot == null) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public PlotDetailResponse getPlotDetails(String plotUuid) {

        Plot plot = plotRepository.findPlotEntityByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "세부정보를 가져올 플롯이 없습니다."));

        return PlotDetailResponse.toDto(plot);
    }

    @Transactional
    public void savePlot(PlotSaveRequest dto) {
        Plot plot = plotRepository.findPlotEntityByPlotUuid(dto.getPlotUuid())
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

        plotRepository.save(plot);
    }


    @Transactional
    public void deletePlot(String plotUuid) {
        plotRepository.deletePlotEntitiesByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "삭제할 플롯이 없습니다."));
    }
}
