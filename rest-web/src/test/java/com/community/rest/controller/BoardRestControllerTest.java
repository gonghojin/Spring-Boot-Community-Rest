package com.community.rest.controller;

import com.community.rest.domain.Board;
import com.community.rest.domain.enums.BoardType;
import com.community.rest.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardRestControllerTest {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BoardRepository boardRepository;

    /**
     * 밑에 방식보다 이 테스트 방식이 나을듯?
     * https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
     */
    @Before
    public void setup() {
        assertNotNull(boardRepository);
        objectMapper = new ObjectMapper();
        //jackson 에서 LocalDateTime 이 JSON 으로 파싱될때 날짜값이 배열로 생성된다. 이를 yyyy-MM-dd'T'HH:mm:ss.SSSZ 포맷으로 변경하기 위한 설정이다.
        //출처: http://syaku.tistory.com/370 [개발자 샤쿠 (Syaku)]
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void 글_등록_테스트() throws Exception {
        this.mvc.perform(
                post("/api/boards")
                        .content(
                                objectMapper
                                        .writeValueAsString(
                                                Board.builder().title("쓰기_제목").subTitle("쓰기_부제목").boardType(BoardType.notice).content("쓰기_내용").build()
                                        )
                        )
                        .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))

        )
        .andExpect(status().isCreated());
       /*  리턴값이 있을 떄 사용하는 방식
       .andExpect(content().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
        .andExpect(jsonPath("$.title").value("쓰기_제목"))
        .andExpect(jsonPath("$.subTitle").value("쓰기_부제목"))
        .andExpect(jsonPath("$.boardType").value("notice"))
        .andExpect(jsonPath("$.createdDate").value(LocalDateTime.now()))
        .andExpect(jsonPath("$.updatedDate").value(LocalDateTime.now()));*/

    }

    @Test
    public void 글_수정_테스트() throws Exception {
        /**
         * JpaRepository.getOne 했을 떄
         * 에러 발생 : Hibernate LazyInitializationException: could not initialize proxy - no Session plz
         * https://stackoverflow.com/questions/48109021/hibernate-lazyinitializationexception-could-not-initialize-proxy-no-session-p
         * 대체 : findById
         *     Board board = boardRepository.findById(1L).orElse(new Board());
         */

        // https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/http-put-test.html
        this.mvc.perform(
                put("/api/boards/{idx}", 1)
                    .content(
                            objectMapper
                                .writeValueAsString(
                                        Board.builder().title("수정_제목").subTitle("수정_부제목").boardType(BoardType.free).content("수정_내용").build()
                                )
                    )
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());

        Board board = boardRepository.findById(1L).orElse(new Board());
        assertThat(board.getTitle()).isEqualTo("수정_제목");
        assertThat(board.getSubTitle()).isEqualTo("수정_부제목");
        assertThat(board.getBoardType()).isEqualTo(BoardType.free);
        assertThat(board.getContent()).isEqualTo("수정_내용");

    }
}
