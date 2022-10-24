/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.organization.repository;

import com.yil.organization.model.OrganizationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationUserDao extends JpaRepository<OrganizationUser, OrganizationUser.Pk> {

    Page<OrganizationUser> findAllById_OrganizationId(Pageable pageable, Long organizationId);

    Page<OrganizationUser> findAllById_UserId(Pageable pageable, Long userId);

    boolean existsAllById_UserIdAndId_OrganizationIdAndManagerTrue(long organizationId, long userId);


    void deleteAllById_OrganizationId(long organizationId);
}
