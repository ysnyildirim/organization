package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ORGANIZATION")
public class Organization extends AbstractEntity {
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
}
