package com.yil.organization.repository;

import com.yil.organization.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Page<Organization> findAllByDeletedTimeIsNull(Pageable pageable);
}
