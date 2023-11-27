package com.semiproject.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.semiproject.entities.Configs;
import com.semiproject.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 설정 정보를 저장하기 위한 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ConfigSaveService {

    private final ConfigsRepository repository;

    /**
     * 지정된 코드에 해당하는 설정 정보를 저장
     *
     * @param code  설정 정보의 코드
     * @param value 저장할 데이터 객체
     * @param <T>   저장할 데이터의 타입
     */
    public <T> void save(String code, T value) {
        // 코드에 해당하는 설정 정보를 조회하거나 새로 생성
        Configs config = repository.findById(code).orElseGet(Configs::new);

        // ObjectMapper를 생성하고 JavaTimeModule을 등록
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        // 데이터 객체를 JSON 문자열로 변환
        String json = null;
        try {
            json = om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 오류가 발생하면 예외를 출력하고 종료
            e.printStackTrace();
        }

        // 설정 정보의 코드와 값을 갱신.
        config.setCode(code);
        config.setValue(json);

        // 설정 정보를 저장하고 변경 사항을 즉시 반영
        repository.saveAndFlush(config);
    }
}
