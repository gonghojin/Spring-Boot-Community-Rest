package com.community.rest.event;

import com.community.rest.domain.Board;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler // 속성으로 (Board.class)를 붙여서, 명시적으로 Board 이벤트라고 표시하는 것도 좋은 방법
public class BoardEventHandler {

    // 생성하기 전의 이벤트
    @HandleBeforeCreate
    public void beforeCreateBoard(Board board) {
        board.setCreatedDateNow();
    }

    // 수정하기 전의 이벤트
    @HandleBeforeSave
    public void beforeSaveBoard(Board board) {
        board.setUpdatedDate();
    }
}
