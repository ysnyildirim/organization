package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Organization")
public class Organization extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "Organization_Sequence_Generator",
            sequenceName = "Seq_Organization",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "Organization_Sequence_Generator")
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;
    @Column(name = "ParentId")
    private Long parentId;
    @Column(name = "OrganizationTypeId", nullable = false)
    private Long organizationTypeId;
}
