package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Notion;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface IUserRepo {
  User getUserById(long id)throws ChangeSetPersister.NotFoundException;

  void deleteUserById(long id);

  User enableUser(long id)throws ChangeSetPersister.NotFoundException;

  User changeUserBalance(long id, BigDecimal value)throws ChangeSetPersister.NotFoundException;

  Notion sendNotionToUser(long userid, NotionDto notionDto);

  User addUser(SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException;

  User addAdmin(SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException;

  User getUserByUsername(String username)throws ChangeSetPersister.NotFoundException;

  List<Notion> getNotionsOfUser(long userid);

  User loadUserByUsername(final String username)throws ChangeSetPersister.NotFoundException;

  List<Notion> getNotionsOfAuthorizedUser(String name)throws ChangeSetPersister.NotFoundException;
}
