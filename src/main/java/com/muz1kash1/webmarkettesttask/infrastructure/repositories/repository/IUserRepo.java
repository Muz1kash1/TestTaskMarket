package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Notion;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import java.math.BigDecimal;
import java.util.List;

public interface IUserRepo {
  User getUserById(long id);

  void deleteUserById(long id);

  User enableUser(long id);

  User changeUserBalance(long id, BigDecimal value);

  Notion sendNotionToUser(long userid, NotionDto notionDto);

  User addUser(SignUpDto signUpDto);

  User addAdmin(SignUpDto signUpDto);

  User getUserByUsername(String username);

  List<Notion> getNotionsOfUser(long userid);

  User loadUserByUsername(final String username);

  List<Notion> getNotionsOfAuthorizedUser(String name);
}
