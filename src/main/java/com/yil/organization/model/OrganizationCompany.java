package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

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
            schema = "ORG")
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
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @ColumnDefault(value = "0")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DATE")
    private Date lastModifyDate;
    @Column(name = "LAST_MODIFY_USER_ID")
    private Long lastModifyUserId;

}
