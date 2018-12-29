package com.community.rest.repository;

import com.community.rest.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @RepositoryRestRource:
 *  스프링 부트 레이터 레스트에서 지원하는 어노테이션
 *  별도의 컨트롤러와 서비스 영역없이 내부적으로 정의되어 있는 로직을 따라 처리
 *  그 로직은 해당 도메인의 정보를 매핑하여 Rest API를 제공하는 역할
 *  - 서비스 단에서 별도의 비즈니스 로직없이 클라이언트와 매핑만 필요할 때 MVC패턴대신 사용
 */
@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {
}
