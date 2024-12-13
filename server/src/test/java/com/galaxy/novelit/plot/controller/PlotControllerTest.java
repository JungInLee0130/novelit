package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.auth.util.JwtUtils;
import com.galaxy.novelit.plot.dto.PlotInfoDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.service.PlotService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlotController.class)
public class PlotControllerTest {

    @MockBean
    private PlotService plotService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup(){

    }

    @Test
    @WithMockUser(username = "류현진")
    @DisplayName("전체 플롯 조회 : keyword가 존재")
    void getPlotListTest() throws Exception{

        // given
        String workspaceUUID = "asdf-asdf";
        String keyword = "김하성";

        List<PlotInfoDto> plotInfoDtoList = new ArrayList<>();

        PlotInfoDto plotInfoDto = new PlotInfoDto("plot-uuid", "HARRY POTTER", "HARRY POTTER 1 EPISODE");

        plotInfoDtoList.add(plotInfoDto);

        PlotListResponseDto plotListResponseDto = new PlotListResponseDto(plotInfoDtoList);

        given(plotService.getPlotListByKeyword(workspaceUUID,keyword)).willReturn(plotListResponseDto);

        //when
        ResultActions perform = mockMvc.perform(get("/plot")
                        .param("workspaceUuid", "asdf-asdf")
                        .param("keyword", "김하성"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plotInfoDtoList[0].plotUuid").value(plotInfoDto.getPlotUuid()))
                .andExpect(jsonPath("$.plotInfoDtoList[0].plotTitle").value(plotInfoDto.getPlotTitle()))
                .andExpect(jsonPath("$.plotInfoDtoList[0].story").value(plotInfoDto.getStory()));


        //then
        verify(plotService, times(1)).getPlotListByKeyword(workspaceUUID,keyword);
        perform.andDo(print());
        //System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
