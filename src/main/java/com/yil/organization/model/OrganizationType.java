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
        name = "ORGANIZATION_TYPE")
public class OrganizationType implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_TYPE_ID",
            schema = "ORG")
    @GeneratedValue(generator = "ORGANIZATION_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @ColumnDefault(value = "0")
    @Column(name = "REAL", nullable = false)
    private Boolean real;
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
