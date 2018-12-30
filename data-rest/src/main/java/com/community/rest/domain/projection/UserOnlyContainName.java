package com.community.rest.domain.projection;

import com.community.rest.domain.User;
import org.springframework.data.rest.core.config.Projection;

// 반환값에 미포함 방법 1 - 상황에 따라 유동적으로 설정
// 주의점 : 1. 도메인과 같은 패키지 내 또는 하위 패키지에 설정하여야 함
//        2. 단일 참조 시(특정 user를 조회시 /api/users/1) 시 적용되지 않음, 즉 전체 유저 조회만 적용
@Projection(name = "getOnlyName", types = {User.class})
public interface UserOnlyContainName {

    String getName();
}
