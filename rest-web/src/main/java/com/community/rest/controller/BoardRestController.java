package com.community.rest.controller;

import com.community.rest.domain.Board;
import com.community.rest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardRestController {

    private final BoardRepository boardRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoards(@PageableDefault Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());
                                                        // 전체 페이지 수, 현재 페이지 번호, 총 게시판 수
        /*
            링크를 추가한 RESTful 데이터를 생성 [HATEOAS 적용]
            만약 게시판과 관련된 페이지의 링크들을 추가한다면?

            "_links": {
                "first" : {
                  "href" : "http://localhost:8081/api/boards?page=0&size=10"
                },
                "self" : {
                  "href" : "http://localhost:8081/api/boards?{page, size, sort, projection}"
                },
                "next" : {
                      "href" : "http://localhost:8081/api/boards?page=1&size=10"
                },
                "last" : {
                      "href" : "http://localhost:8081/api/boards?page=20&size=10"
                },
            }
         */
        PagedResources<Board> resources = new PagedResources<>(boards.getContent(), pageMetadata);
        resources.add(linkTo(methodOn(BoardRestController.class)
                .getBoards(pageable))
                .withSelfRel());

        return ResponseEntity.ok(resources);
    }


}
