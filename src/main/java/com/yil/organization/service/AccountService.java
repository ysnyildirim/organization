/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.organization.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AccountService {

    private static String url = "http://localhost:8082/api/account/v1";

    public boolean existsPermission(long permissionId, long userId) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8082/api/account/v1/users/{id}/permission-id={permissionId}";
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class, Map.of("id", userId, "permissionId", permissionId));
        return responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

}
