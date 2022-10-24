/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.organization.exception;

import com.yil.organization.base.ApiException;
import com.yil.organization.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@ApiException(code = ErrorCode.OrganizationUserNotFound)
public class OrganizationUserNotFoundException extends Exception {
}
