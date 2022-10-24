package com.yil.organization.repository;

import com.yil.organization.model.OrganizationCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationCompanyDao extends JpaRepository<OrganizationCompany, Long> {
    Page<OrganizationCompany> findAllByOrganizationId(Pageable pageable, Long organizationId);

    Page<OrganizationCompany> findAllByCompanyId(Pageable pageable, Long companyId);

    void deleteAllByOrganizationId(long organizationId);
}
