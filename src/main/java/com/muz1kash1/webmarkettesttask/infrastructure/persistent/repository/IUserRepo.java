package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Notion;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import java.math.BigDecimal;
import java.util.List;

public interface IUserRepo {
  User getUserById(long id);

  void deleteUserById(long id);

  User disableUser(long id);

  User changeUserBalance(long id, BigDecimal value);

  Notion sendNotionToUser(long userid, NotionDto notionDto);

  User addUser(SignUpDto signUpDto);

  User getUserByUsername(String username);

  List<Notion> getNotionsOfUser(long userid);
}
