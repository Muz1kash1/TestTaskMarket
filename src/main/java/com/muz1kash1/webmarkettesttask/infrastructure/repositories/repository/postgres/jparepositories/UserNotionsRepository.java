package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.UserNotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserNotionsRepository extends JpaRepository<UserNotions, Long> {

  @Query(
    value = "SELECT notions.id,header,notion_text,notion_date from notions inner join user_notions on notions.id = user_notions.id where user_notions.user_id =:userId ",
    nativeQuery = true)
  List<Long> getIdOfNotionsOfUser(@Param(("userId")) long userid);
  List<UserNotions> findAllByUserId(long id);
}
