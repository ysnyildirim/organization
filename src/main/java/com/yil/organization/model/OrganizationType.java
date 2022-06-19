package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ORGANIZATION_TYPE")
public class OrganizationType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "ORGANIZATION_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_ORGANIZATION_TYPE_ID",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "ORGANIZATION_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}
