package com.yil.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCompanyDto {
    private Long id;
    private Long organizationId;
    private Long companyId;
}
