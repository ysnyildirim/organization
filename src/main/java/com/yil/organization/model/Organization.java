package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_PARENT_ID", columnList = "PARENT_ID")
        })
public class Organization implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_ID",
            schema = "ORG")
    @GeneratedValue(generator = "ORGANIZATION_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "PARENT_ID")
    private Long parentId;
    @Column(name = "NAME", nullable = false, length = 1000)
    private String name;
    @Column(name = "DESCRIPTION", length = 4000)
    private String description;
    @Column(name = "ORGANIZATION_TYPE_ID", nullable = false)
    private Integer organizationTypeId;
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
