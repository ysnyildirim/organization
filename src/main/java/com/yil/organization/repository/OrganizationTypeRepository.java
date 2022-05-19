package com.yil.organization.repository;

import com.yil.organization.model.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationTypeRepository extends JpaRepository<OrganizationType, Long> {
    List<OrganizationType> findAllByDeletedTimeIsNull();

}
