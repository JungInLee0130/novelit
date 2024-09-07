package com.galaxy.novelit.plot.dto.response;

import com.galaxy.novelit.plot.dto.PlotInfoDto;
import com.galaxy.novelit.plot.entity.Plot;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotListResponseDto {
    private List<PlotInfoDto> plotInfoDtoList;

    public static PlotListResponseDto entityToDto(List<Plot> entityList) {
        return PlotListResponseDto.builder()
            .plotInfoDtoList(listToDtoList(entityList))
            .build();
    }

    public static List<PlotInfoDto> listToDtoList(List<Plot> entityList) {
        List<PlotInfoDto> dtoList = new ArrayList<>();
        for (Plot entity : entityList) {
            dtoList.add(PlotInfoDto.entityToDto(entity));
        }
        return dtoList;
    }
}
