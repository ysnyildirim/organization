package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "OrganizationCompany")
public class OrganizationCompany extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "OrganizationCompany_Sequence_Generator",
            sequenceName = "Seq_OrganizationCompany",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "OrganizationCompany_Sequence_Generator")
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;
    @Column(name = "OrganizationId", nullable = false)
    private Long organizationId;
    @Column(name = "CompanyId", nullable = false)
    private Long companyId;

}
