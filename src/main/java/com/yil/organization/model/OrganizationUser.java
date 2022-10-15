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
        name = "ORGANIZATION_USER",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_USER_USER_ID", columnList = "USER_ID"),
                @Index(name = "IDX_ORGANIZATION_USER_ORGANIZATION_ID", columnList = "ORGANIZATION_ID")
        })
public class OrganizationUser implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_USER_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_USER_ID",
            schema = "ORG")
    @GeneratedValue(generator = "ORGANIZATION_USER_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @ColumnDefault(value = "0")
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;
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
