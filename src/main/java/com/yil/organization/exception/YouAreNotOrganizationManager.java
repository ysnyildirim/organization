/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.organization.exception;

import com.yil.organization.base.ApiException;
import com.yil.organization.base.ErrorCode;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@ApiException(code = ErrorCode.YouAreNotOrganization)
@ApiResponse(responseCode = "5000003", description = "Organizasyon yöneticisi değilsiniz!")
public class YouAreNotOrganizationManager extends Throwable {
}
