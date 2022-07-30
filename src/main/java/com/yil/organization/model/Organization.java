package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_ORGANIZATION_TYPE_ID", columnList = "ORGANIZATION_TYPE_ID")
        })
public class Organization implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_ID",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "ORGANIZATION_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "PARENT_ID")
    private Long parentId;
    @Column(name = "ORGANIZATION_TYPE_ID", nullable = false)
    private Long organizationTypeId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
