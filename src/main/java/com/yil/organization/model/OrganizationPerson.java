package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.Data;
import org.hibernate.annotations.Comment;

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
public class OrganizationPerson implements IEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_PERSON_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_PERSON_ID",
            schema = "ORG")
    @GeneratedValue(generator = "ORGANIZATION_PERSON_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;
    @Column(name = "PERSON_ID", nullable = false)
    private Long personId;
    @Comment("Organizasyona giriş tarihi")
    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE")
    private Date fromDate;
    @Comment("Organizasyondan çıkış tarihi")
    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE")
    private Date toDate;
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
