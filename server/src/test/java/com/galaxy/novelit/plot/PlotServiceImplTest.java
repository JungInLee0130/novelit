package com.galaxy.novelit.plot;

import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.fixture.PlotFixture;
import com.galaxy.novelit.plot.repository.PlotRepository;
import com.galaxy.novelit.plot.service.PlotServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlotServiceImplTest {

    @Mock
    PlotRepository plotRepository;

    @Spy
    @InjectMocks
    PlotServiceImpl plotService;

    private Plot plot;

    @BeforeEach
    void setup(){
        plot = PlotFixture.DEFAULT.getPlot();
    }

    @DisplayName("플롯 저장/업데이트 테스트 + 더티 체킹")
    @Test
    @Transactional
    public void savePlotTest(){
        //given
        PlotSaveRequestDto request = PlotSaveRequestDto.builder()
                .plotUuid("asdf-asdf")
                .plotTitle("해리포터 2장")
                .build();

        given(plotRepository.findByPlotUuid(plot.getPlotUuid()))
                .willReturn(Optional.of(plot));

        // when
        plotService.savePlot(request);

        // then
        Assertions.assertThat(plot.getPlotTitle())
                .isEqualTo("해리포터 2장");
        //then(plotService).should(times(1)).savePlot(request);
    }
}
