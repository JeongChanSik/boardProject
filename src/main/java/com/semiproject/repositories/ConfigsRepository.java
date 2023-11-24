package com.semiproject.repositories;

import com.semiproject.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 설정 정보에 대한 JPA Repository 인터페이스입니다.
 */
public interface ConfigsRepository extends JpaRepository<Configs, String> {

}
