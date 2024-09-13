package com.galaxy.novelit.comment.controller;

import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import com.galaxy.novelit.comment.service.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentServiceImpl commentService;

    private String userUUID;
    private MockMvc mockMvc;


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        userUUID = "리어카";
    }

    @DisplayName("댓글 추가 실패 : commentContent 길이 1미만")
    @Test
    void 댓글추가실패() throws Exception{
        //given
        CommentAddRequestDto request = CommentAddRequestDto.builder()
                .spaceUUID("1234")
                .directoryUUID("1234")
                .commentContent("")
                .commentNickname("김하성")
                .build();


        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request))
        );


        resultActions.andExpect(status().isBadRequest());

        //then
    }
}
