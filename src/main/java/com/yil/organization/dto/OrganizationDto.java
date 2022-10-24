package com.yil.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto implements Serializable {
    private Long id;
    private Long parentId;
    private Integer organizationTypeId;
    private String name;
    private String description;
}
