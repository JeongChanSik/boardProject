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

@Service
@RequiredArgsConstructor
public class ConfigInfoService {

    private final ConfigsRepository repository;

    public <T> T get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }

    public <T> T get(String code, TypeReference<T> typeReference) {
        return get(code, null, typeReference);
    }

    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {

        try {
            Configs configs = repository.findById(code).orElse(null);
            if (configs == null || StringUtils.hasText(configs.getValue())) {
                return null;
            }

            String json = configs.getValue();

            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());


            try {
                T data = clazz == null ? om.readValue(json, typeReference) : om.readValue(json, clazz);
                 return data;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
