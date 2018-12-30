package com.community.rest.domain.controller;

import com.community.rest.domain.Board;
import com.community.rest.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController // '데이터 레스트'에서 지원하는 @RestController를 대체하는 역할
public class BoardRestController {

    private BoardRepository boardRepository;

    public BoardRestController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    public @ResponseBody PagedResources<Board> simpleBoard(@PageableDefault Pageable pageable) {
        Page<Board> boardList = boardRepository.findAll(pageable);

        PageMetadata pageMetadata = new PageMetadata(
                pageable.getPageSize(), boardList.getNumber(), boardList.getTotalElements()
        ); // (전체 페이지 수, 현재 페이지 번호, 총 게시판 수)

        PagedResources<Board> resources = new PagedResources<>(
                boardList.getContent(), pageMetadata
        );
        resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable))
                .withSelfRel()
        );

        return resources;
    }


}
