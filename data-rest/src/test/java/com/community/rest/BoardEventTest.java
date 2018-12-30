package com.community.rest;

import com.community.rest.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataRestApplication.class // 데이터 레스트를 테스트하기 위해, 시큐리티 설정이 들어있는 클래스를 주입
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase // H2가 build.gradle의 클래스 경로에 포함되어 있으면, 자동으로 H2를 테스트 데이터 베이스로 지정
                            // 만약 이 어노테이션을 사용하지 않는다면, 테스트에서 Board를 저장할 때마다 실제 데이터베이스에 반영
                            // But, 나는 test의 application.yml을 잡아놨기 떄문에 해당하지 않음
public class BoardEventTest {
    /**
     *  TestRestTemplate은 RestTemplate을 래핑한 객체로서,
     *  [GET, POST, PUT DELETE]와 같은 HttpRequest를 편하게 테스트하도록 도와준다.
     *  시큐리티 설정에 ADMIN으로 생성한 'gongdel'의 정보를 파라미터로 넣어주면, 해당 아이디로 권한 인증이 통과
     *  (save 메소드에 권한을 ADMIN으로 설정했기 때문에 필요)
     */
    private TestRestTemplate testRestTemplate =
                new TestRestTemplate("gongdel", "test");

    @Test
    public void 저장할때_이벤트가_적용되어_생성날짜가_생성되는가() {
        Board createdBoard = createBoard();
        assertNotNull(createdBoard.getCreatedDate());
    }

    private Board createBoard() {
        Board board = Board.builder()
                        .title("저장 이벤트 테스트")
                        .build();

        return testRestTemplate.postForObject("http://localhost:8081/api/boards"
                            , board, Board.class
        );
    }

    @Test
    public void 수정할때_이벤트가_적용되어_수정날짜가_생성되는가() {
        Board createBoard = createBoard();
        Board updateBoard = updateBoard(createBoard);
        assertNotNull(updateBoard.getUpdatedDate());
    }

    private Board updateBoard(Board createBoard) {
        String updateUri = "http://localhost:8081/api/boards/1";
        testRestTemplate.put(updateUri, createBoard);

        return testRestTemplate.getForObject(updateUri, Board.class);
    }


}
