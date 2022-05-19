package com.yil.organization.repository;

import com.yil.organization.model.OrganizationCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationCompanyRepository extends JpaRepository<OrganizationCompany, Long> {
    Page<OrganizationCompany> findAllByAndOrganizationIdAndDeletedTimeIsNull(Pageable pageable, Long organizationId);
}
