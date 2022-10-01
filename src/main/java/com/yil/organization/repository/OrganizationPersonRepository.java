package com.yil.organization.repository;

import com.yil.organization.model.OrganizationPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrganizationPersonRepository extends JpaRepository<OrganizationPerson, Long> {
    Page<OrganizationPerson> findAllByOrganizationId(Pageable pageable, Long organizationId);

    Optional<OrganizationPerson> findByIdAndOrganizationId(long id, long organizationId);

    boolean existsByOrganizationIdAndPersonIdAndManagerTrue(long organizationId, long personId);

    @Modifying
    @Transactional
    void deleteByIdAndOrganizationId(long id, long organizationId);
}
