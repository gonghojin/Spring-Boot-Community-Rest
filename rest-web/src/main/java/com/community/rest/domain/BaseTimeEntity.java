package com.community.rest.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 보통 Entity에는 해당 데이터의 생성시간과 수정시간을 포함시킨다.
 * 언제 만들어 졌는지? 언제 수정되었는지? 등은 차후 유지 보수에 있어서 굉장히 중요한 정보이기 떄문
 * 그렇다 보니 매번 DB에 insert하기 전, update하기 전에 날짜 데이터를 등록 / 수정하는 코드가 여기저기 들어가게 되는데.
 * 반복적인 코드가 모든 테이블과 서비스 메소드에 포함되면 너무 귀찮고 난잡하다.
 * 이를 해결하기 위해 JPA Auduting을 사용
 */
@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, 필드들도 컬럼으로 인식하도록 해줌
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능을 포함
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
