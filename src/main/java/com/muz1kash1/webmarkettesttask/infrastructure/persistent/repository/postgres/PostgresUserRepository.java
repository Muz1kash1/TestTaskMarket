package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.UserNotions;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IUserRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.NotionsRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.UserNotionsRepository;
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
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres")
public class PostgresUserRepository implements IUserRepo {
  private final com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.UserRepository
    userRepository;
  private final NotionsRepository notionsRepository;
  private final UserNotionsRepository userNotionsRepository;

  @Override
  public User getUserById(long id) {

    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserById(id)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );

  }

  @Override
  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User disableUser(long id) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
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
      user.isEnabled()
    );
  }

  @Override
  public User changeUserBalance(long id, BigDecimal value) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
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
      user.isEnabled()
    );
  }

  @Transactional
  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Notion sendNotionToUser(final long userid,
                                                                              final NotionDto notionDto) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Notion
      notion = new com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Notion(
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
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = new
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User(
      signUpDto.getUsername(),
      signUpDto.getMail(),
      signUpDto.getPassword(),
      BigDecimal.ZERO,
      true
    );
    userRepository.save(user);
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User userToReturn = userRepository
      .findUserByUsername(user.getUsername())
      .get();
    return new User(
      userToReturn.getId(),
      userToReturn.getUsername(),
      userToReturn.getMail(),
      userToReturn.getPassword(),
      userToReturn.getBalance(),
      userToReturn.isEnabled()
    );
  }

  @Override
  public User getUserByUsername(String username) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserByUsername(username)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );
  }

  @Override
  public List<com.muz1kash1.webmarkettesttask.model.domain.Notion> getNotionsOfUser(final long userid) {
    List<Long> notionIds = userNotionsRepository.getIdOfNotionsOfUser(userid);

    List<com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Notion> notions = new ArrayList<>();
    for (
      Long notionId : notionIds
    ) {
      notions.add(notionsRepository.getReferenceById(notionId));
    }

    List<com.muz1kash1.webmarkettesttask.model.domain.Notion> notionList = new ArrayList<>();
    for (
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Notion notion : notions
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
}
