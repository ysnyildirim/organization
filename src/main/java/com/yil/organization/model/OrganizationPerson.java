package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import com.yil.organization.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION_PERSON",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_PERSON_PERSON_ID", columnList = "PERSON_ID"),
                @Index(name = "IDX_ORGANIZATION_PERSON_ORGANIZATION_ID", columnList = "ORGANIZATION_ID")
        })
public class OrganizationPerson  implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_PERSON_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_PERSON_ID",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "ORGANIZATION_PERSON_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;
    @Column(name = "PERSON_ID", nullable = false)
    private Long personId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
