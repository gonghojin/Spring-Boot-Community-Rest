package com.community.rest.repository;

import com.community.rest.domain.User;
import com.community.rest.domain.projection.UserOnlyContainName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// excerpt == 발췌하다
/** 적용 전 :
 *      _embedded" : {
 *     "users" : [ {
 *       "createdDate" : "2018-12-30T15:10:46",
 *       "updatedDate" : "2018-12-30T15:10:46",
 *       "name" : "gongdel",
 *          ...
 *
 *   적용 후 :
 *      "users" : [ {
 *       "name" : "gongdel",
 *          ...
 */
@RepositoryRestResource(excerptProjection = UserOnlyContainName.class)
public interface UserRepository extends JpaRepository<User, Long> {
}
