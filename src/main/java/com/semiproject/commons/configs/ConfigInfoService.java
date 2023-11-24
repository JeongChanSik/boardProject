package com.semiproject.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.semiproject.entities.Configs;
import com.semiproject.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 설정 정보를 조회하기 위한 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ConfigInfoService {

    private final ConfigsRepository repository;

    /**
     * 지정된 코드에 해당하는 설정 정보를 가져온다.
     *
     * @param code  설정 정보의 코드
     * @param clazz 반환할 데이터의 클래스
     * @param <T>   반환할 데이터의 타입
     * @return      설정 정보에 대한 데이터 객체 또는 null (존재하지 않거나 비어 있을 경우)
     */
    public <T> T get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }

    /**
     * 지정된 코드에 해당하는 설정 정보를 가져옴
     *
     * @param code          설정 정보의 코드
     * @param typeReference 반환할 데이터의 TypeReference
     * @param <T>           반환할 데이터의 타입
     * @return              설정 정보에 대한 데이터 객체 또는 null (존재하지 않거나 비어 있을 경우)
     */
    public <T> T get(String code, TypeReference<T> typeReference) {
        return get(code, null, typeReference);
    }

    /**
     * 지정된 코드에 해당하는 설정 정보를 가져온다.
     *
     * @param code          설정 정보의 코드
     * @param clazz         반환할 데이터의 클래스
     * @param typeReference 반환할 데이터의 TypeReference
     * @param <T>           반환할 데이터의 타입
     * @return              설정 정보에 대한 데이터 객체 또는 null (존재하지 않거나 비어 있을 경우)
     */
    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        // 코드에 해당하는 설정 정보를 조회
        Configs config = repository.findById(code).orElse(null);

        // 설정 정보가 존재하지 않거나 값이 비어있으면 null을 반환
        if (config == null || StringUtils.hasText(config.getValue())) {
            return null;
        }

        // 설정 정보의 JSON 값을 가져옵니다.
        String json = config.getValue();

        // ObjectMapper를 생성하고 JavaTimeModule을 등록
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        try {
            // clazz가 null이면 TypeReference를 사용하여 데이터를 읽고, 그렇지 않으면 clazz를 사용하여 데이터를 읽는다.
            T data = clazz == null ? om.readValue(json, typeReference) : om.readValue(json, clazz);

            // 읽은 데이터를 반환
            return data;

        } catch (JsonProcessingException e) {
            // JSON 파싱 중 오류가 발생하면 예외를 출력하고 null을 반환
            e.printStackTrace();
            return null;
        }
    }
}
