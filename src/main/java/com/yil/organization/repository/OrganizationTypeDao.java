package com.yil.organization.repository;

import com.yil.organization.model.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationTypeDao extends JpaRepository<OrganizationType, Integer> {
}
