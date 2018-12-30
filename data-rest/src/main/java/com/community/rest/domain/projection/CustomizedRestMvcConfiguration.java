/*
package com.community.rest.domain.projection;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

// @RepositoryRestResource 어노테이션의 excerptProjection의 단일 참조 시만 적용되는 단점 보완
// 수동으로 프로젝션 등록 [참고용으로만 알기, 가장 추천하는 방법은 @RepositoryRestController를 사용하여
// 컨트롤러 단에서 제어하기
@Configuration
public class CustomizedRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.getProjectionConfiguration().addProjection(UserOnlyContainName.class);
    }
}
*/
