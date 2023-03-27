package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Notion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotionsRepository extends JpaRepository<Notion, Long> {
}
