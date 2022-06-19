package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ORGANIZATION_PERSON")
public class OrganizationPerson extends AbstractEntity {
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
}
