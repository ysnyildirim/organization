package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION")
public class Organization implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_ID",
            schema = "ORG")
    @GeneratedValue(generator = "ORGANIZATION_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "PARENT_ID")
    private Long parentId;
    @Column(name = "ORGANIZATION_TYPE_ID", nullable = false)
    private Long organizationTypeId;
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
    @ColumnDefault(value = "true")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DATE")
    private Date lastModifyDate;
    @Column(name = "LAST_MODIFY_USER_ID")
    private Long lastModifyUserId;

}
