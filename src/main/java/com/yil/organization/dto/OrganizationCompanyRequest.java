package com.yil.organization.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrganizationCompanyRequest {
    private Date fromDate;
    private Date toDate;
}
