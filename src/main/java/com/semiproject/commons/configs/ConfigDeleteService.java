package com.semiproject.commons.configs;

import com.semiproject.entities.Configs;
import com.semiproject.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 설정 데이터를 삭제하기 위한 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    /**
     * 제공된 코드에 기반하여 설정 데이터를 삭제
     *
     * @param code 삭제할 설정 데이터의 코드
     */
    public void delete(String code) {
        // 코드에 기반하여 설정 데이터를 검색
        Configs configs = repository.findById(code).orElse(null);

        // 설정 데이터가 존재하는지 확인
        if (configs == null) {
            // 찾을 수 없으면 삭제를 수행하지 않고 반환
            return;
        }

        // 설정 데이터를 삭제
        repository.delete(configs);

        // 변경 사항을 저장소에 즉시 반영
        repository.flush();
    }
}
