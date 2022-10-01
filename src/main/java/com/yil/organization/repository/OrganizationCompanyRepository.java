package com.yil.organization.repository;

import com.yil.organization.model.OrganizationCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationCompanyRepository extends JpaRepository<OrganizationCompany, Long> {
    Page<OrganizationCompany> findAllByAndOrganizationId(Pageable pageable, Long organizationId);

    Optional<OrganizationCompany> findByIdAndAndOrganizationId(Long id, Long organizationId);

    void deleteByIdAndOrganizationId(long id, long organizationId);
}
