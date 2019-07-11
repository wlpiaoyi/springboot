package org.wlpiaoyi.springboot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityJpaRepository<T, PK> extends JpaRepository<T, PK> {
}