/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */
package com.yil.organization.base;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OrganizationUserNotFound(5000006, "Organizasyon kullanıcısı bulunamadı!"),
    OrganizationPersonNotFound(5000005, "Organizasyon kişi bulunamadı!"),
    OrganizationCompanyNotFound(5000004, "Organizasyon şirketi bulunamadı!"),
    YouAreNotOrganization(5000003, "Organizasyon yöneticisi değilsiniz!"),
    OrganizationTypeNotFound(5000002, "Organizasyon türü bulunamadı!"),
    YouDoNotHavePermission(5000001, "Yetkiniz bulunmamaktadır!"),
    OrganizationNotFound(5000000, "Organizasyon bulunamadı!");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
