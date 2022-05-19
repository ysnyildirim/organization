package com.yil.organization.model;

import com.yil.organization.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "OrganizationPerson")
public class OrganizationPerson extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "OrganizationPerson_Sequence_Generator",
            sequenceName = "Seq_OrganizationPerson",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "OrganizationPerson_Sequence_Generator")
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;
    @Column(name = "OrganizationId", nullable = false)
    private Long organizationId;
    @Column(name = "PersonId", nullable = false)
    private Long personId;
    @Column(name = "StartDate")
    private Date startDate;
    @Column(name = "EndDate")
    private Date endDate;
}
