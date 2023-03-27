package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Notion;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.UserNotions;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IUserRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.NotionsRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.UserNotionsRepository;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres")
public class PostgresUserRepository implements IUserRepo {
  private final com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.UserRepository
    userRepository;
  private final NotionsRepository notionsRepository;
  private final UserNotionsRepository userNotionsRepository;

  @Override
  public User getUserById(long id) {

    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = userRepository
      .findUserById(id)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled(),
      user.getRoles()
    );

  }

  @Override
  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User enableUser(long id) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = userRepository
      .findUserById(id)
      .get();
    user.setEnabled(true);
    userRepository.save(user);
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled(),
      user.getRoles()
    );
  }

  @Override
  public User changeUserBalance(long id, BigDecimal value) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = userRepository
      .findUserById(id)
      .get();
    user.setBalance(user.getBalance().add(value));
    userRepository.save(user);
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled(),
      user.getRoles()
    );
  }

  @Transactional
  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Notion sendNotionToUser(final long userid,
                                                                              final NotionDto notionDto) {
    Notion
      notion = new Notion(
      notionDto.getHeader(),
      notionDto.getNotionDate(),
      notionDto.getNotionText()
    );
    notionsRepository.save(notion);
    userNotionsRepository.save(new UserNotions(
      userid,
      notion.getId()
    ));
    return new com.muz1kash1.webmarkettesttask.model.domain.Notion(
      notion.getId(),
      notion.getHeader(),
      notion.getNotionDate(),
      notion.getNotionText()
    );
  }

  @Override
  public User addUser(SignUpDto signUpDto) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = new
      com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User(
      signUpDto.getUsername(),
      signUpDto.getMail(),
      signUpDto.getPassword(),
      BigDecimal.ZERO,
      true,
      "USER");
    userRepository.save(user);
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User userToReturn = userRepository
      .findUserByUsername(user.getUsername())
      .get();
    return new User(
      userToReturn.getId(),
      userToReturn.getUsername(),
      userToReturn.getMail(),
      userToReturn.getPassword(),
      userToReturn.getBalance(),
      userToReturn.isEnabled(),
      user.getRoles()
    );
  }

  @Override
  public User addAdmin(SignUpDto signUpDto) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = new
      com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User(
      signUpDto.getUsername(),
      signUpDto.getMail(),
      signUpDto.getPassword(),
      BigDecimal.ZERO,
      true,
      "USER,ADMIN"
    );
    userRepository.save(user);
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User userToReturn = userRepository
      .findUserByUsername(user.getUsername())
      .get();
    return new User(
      userToReturn.getId(),
      userToReturn.getUsername(),
      userToReturn.getMail(),
      userToReturn.getPassword(),
      userToReturn.getBalance(),
      userToReturn.isEnabled(),
      user.getRoles()
    );
  }

  @Override
  public User getUserByUsername(String username) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user = userRepository
      .findUserByUsername(username)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled(),
      user.getRoles()
    );
  }

  @Override
  public List<com.muz1kash1.webmarkettesttask.model.domain.Notion> getNotionsOfUser(final long userid) {
    List<Long> notionIds = userNotionsRepository.getIdOfNotionsOfUser(userid);

    List<Notion> notions = new ArrayList<>();
    for (
      Long notionId : notionIds
    ) {
      notions.add(notionsRepository.getReferenceById(notionId));
    }

    List<com.muz1kash1.webmarkettesttask.model.domain.Notion> notionList = new ArrayList<>();
    for (
      Notion notion : notions
    ) {
      notionList.add(new com.muz1kash1.webmarkettesttask.model.domain.Notion(
        notion.getId(),
        notion.getHeader(),
        notion.getNotionDate(),
        notion.getNotionText()
      ));
    }
    return notionList;
  }

  @Override
  public User loadUserByUsername(final String username) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User user =
      userRepository
      .findUserByUsername(username).
      get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled(),
      user.getRoles()
    );
  }
}
