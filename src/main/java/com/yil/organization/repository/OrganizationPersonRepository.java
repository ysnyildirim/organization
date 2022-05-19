package com.yil.organization.repository;

import com.yil.organization.model.OrganizationPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationPersonRepository extends JpaRepository<OrganizationPerson, Long> {
    Page<OrganizationPerson> findAllByAndOrganizationIdAndDeletedTimeIsNull(Pageable pageable, Long organizationId);
}
