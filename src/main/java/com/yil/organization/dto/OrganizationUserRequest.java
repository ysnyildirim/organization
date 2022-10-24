/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.organization.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrganizationUserRequest {
    @NotNull
    private Boolean manager = false;
}
