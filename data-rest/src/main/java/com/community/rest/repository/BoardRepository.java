package com.community.rest.repository;

import com.community.rest.domain.Board;
import com.community.rest.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @RepositoryRestRource:
 *  스프링 부트 레이터 레스트에서 지원하는 어노테이션
 *  별도의 컨트롤러와 서비스 영역없이 내부적으로 정의되어 있는 로직을 따라 처리
 *  그 로직은 해당 도메인의 정보를 매핑하여 Rest API를 제공하는 역할
 *  - 서비스 단에서 별도의 비즈니스 로직없이 클라이언트와 매핑만 필요할 때 MVC패턴대신 사용
 *
 *  기본 uri는 application.yml에 잡은 basePath + /boards로 설정됨
 *  변경을 하고 싶을 경우?
 *  ex) notice로 원한다면?
 *  - @RepositoryRestResource(path = "notice", ~~)
 *      /api/notice로 uri가 변경됨
 */
@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * save() 메소드에 ADMIN 권한 지정하기
     */
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Board> S save(S entity);

    /**
     * 모든 검색 쿼리 메서드는 search 하위로 표현된다.
     * 또한 별도의 @RestResource(path = ~)를 설정하지 않으면 기본값에 해당 메서드명이 적용
     * ex)
     *  ~ /api/boards/search/findByTitle?title=게시글1
     */
    @RestResource(path = "query")
    List<Board> findByTitle(@Param("title") String title);

}
