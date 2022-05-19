package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "OrganizationType")
public class OrganizationType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "OrganizationType_Sequence_Generator",
            sequenceName = "Seq_OrganizationType",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "OrganizationType_Sequence_Generator")
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
}
