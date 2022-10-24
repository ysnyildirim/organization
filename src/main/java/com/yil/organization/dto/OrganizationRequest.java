package com.yil.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequest {
    private Long parentId;
    @NotNull
    private Integer organizationTypeId;
    @NotNull
    @Length(min = 1, max = 1000)
    private String name;
    @Length(max = 4000)
    private String description;
}
