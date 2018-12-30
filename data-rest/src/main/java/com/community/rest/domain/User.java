package com.community.rest.domain;

import com.community.rest.domain.enums.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table                                                             // 발생 오류 : http://developerhjg.tistory.com/204
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})     // 1. https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
public class User extends BaseTimeEntity implements Serializable { // 2. https://zero2hex.github.io/2016/07/25/daily-devnote-20160725/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idx;

    @Column
    private String name;

    @Column
    @JsonIgnore // 반환값에 미포함 방법 1
    /**
     * ex) 적용 전
     *   "users" : [ {
     *       "createdDate" : "2018-12-30T15:10:46",
     *       "updatedDate" : "2018-12-30T15:10:46",
     *       "name" : "gongdel",
     *       "password" : "test",
     *       "email" : "x@gmail.com",
     *          ....
     *       }
     *     적용 후
     *     "users" : [ {
     *       "createdDate" : "2018-12-30T15:10:46",
     *       "updatedDate" : "2018-12-30T15:10:46",
     *       "name" : "gongdel",
     *       "email" : "x@gmail.com",
     *           ...
     */
    private String password;
    @Column
    private String email;

    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder
    public User(String name, String password, String email, String principal, SocialType socialType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
    }
}

