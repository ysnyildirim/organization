package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION_COMPANY",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_COMPANY_ORGANIZATION_ID", columnList = "ORGANIZATION_ID"),
                @Index(name = "IDX_ORGANIZATION_COMPANY_COMPANY_ID", columnList = "COMPANY_ID")
        })
public class OrganizationCompany implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_COMPANY_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_COMPANY_ID",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "ORGANIZATION_COMPANY_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;
    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;
    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE")
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE")
    private Date toDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;

}
