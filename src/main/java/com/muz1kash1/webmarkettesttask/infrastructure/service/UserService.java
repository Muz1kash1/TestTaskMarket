package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IUserRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Notion;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import com.muz1kash1.webmarkettesttask.model.dto.UserDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
  private final IUserRepo userRepository;

  private static UserDto getUserDtoFromDomain(final User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getMailAddress(),
        user.getBalance(),
        user.getPassword(),
        user.isEnabled(),
        user.getRoles());
  }

  public UserDto signUp(SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.addUser(signUpDto);
    return getUserDtoFromDomain(user);
  }

  public UserDto adminSignUp(SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.addAdmin(signUpDto);
    return getUserDtoFromDomain(user);
  }

  public UserDto findByUsername(String username)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.getUserByUsername(username);
    if (username.equals(user.getUsername())) {
      return getUserDtoFromDomain(user);
    }
    throw Problem.valueOf(Status.I_AM_A_TEAPOT); // Пасхалочка
  }

  public UserDto changeUserBalance(final long userid, final BigDecimal valueOf)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.changeUserBalance(userid, valueOf);
    return getUserDtoFromDomain(user);
  }

  public UserDto getUserById(final long userid)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.getUserById(userid);
    return getUserDtoFromDomain(user);
  }

  public void deleteUserById(final long userService) {
    userRepository.deleteUserById(userService);
  }

  public UserDto freezeUserById(final long userid)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.getUserById(userid);
    user.setEnabled(false);
    return getUserDtoFromDomain(user);
  }

  public NotionDto sendNotionToUser(final long userid, final NotionDto notionDto) {
    Notion notion = userRepository.sendNotionToUser(userid, notionDto);
    return new NotionDto(
        notion.getId(), notion.getHeader(), notion.getNotionDate(), notion.getNotionText());
  }

  public List<NotionDto> getUserNotions(final long userid) {
    List<Notion> userNotions = userRepository.getNotionsOfUser(userid);
    List<NotionDto> notionDtos = new ArrayList<>();
    for (Notion userNotion : userNotions) {
      notionDtos.add(
          new NotionDto(
              userNotion.getId(),
              userNotion.getHeader(),
              userNotion.getNotionDate(),
              userNotion.getNotionText()));
    }
    return notionDtos;
  }

  public List<NotionDto> getAuthorizedUserNotions(final String name)throws ChangeSetPersister.NotFoundException {
    List<Notion> userNotions = userRepository.getNotionsOfAuthorizedUser(name);
    List<NotionDto> notionDtos = new ArrayList<>();
    for (Notion userNotion : userNotions) {
      notionDtos.add(
          new NotionDto(
              userNotion.getId(),
              userNotion.getHeader(),
              userNotion.getNotionDate(),
              userNotion.getNotionText()));
    }
    return notionDtos;
  }

  public UserDto getUserByUsername(final String name)throws ChangeSetPersister.NotFoundException {
    User user = userRepository.loadUserByUsername(name);
    return getUserDtoFromDomain(user);
  }
}
