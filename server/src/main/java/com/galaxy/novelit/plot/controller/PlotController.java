package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.service.PlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/plot")
@RequiredArgsConstructor
public class PlotController {

    private final PlotService plotService;

    @GetMapping
    public ResponseEntity<PlotListResponseDto> getPlotList(@RequestParam("workspaceUuid") String workspaceUuid,
                                                           @RequestParam(required = false, name = "keyword") @Length(max = 200) String keyword) {
        if (keyword != null) {
            return ResponseEntity.ok(plotService.getPlotListByKeyword(workspaceUuid, keyword));
        }
        return ResponseEntity.ok(plotService.getPlotList(workspaceUuid));
    }

    @PostMapping
    public ResponseEntity<Void> createPlot(@RequestBody @Valid PlotCreateRequestDto plotCreateRequestDto) {
        plotService.createPlot(plotCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<PlotDetailsResponseDto> getPlotDetails(@RequestParam("plotUuid") String plotUuid) {
        return ResponseEntity.ok(plotService.getPlotDetails(plotUuid));
    }

    @PutMapping
    public ResponseEntity<Void> savePlot(@RequestBody @Valid PlotSaveRequestDto plotSaveRequestDto) {
        plotService.savePlot(plotSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlot(@RequestParam("plotUuid") String plotUuid) {
        plotService.deletePlot(plotUuid);
        return ResponseEntity.ok().build();
    }
}
