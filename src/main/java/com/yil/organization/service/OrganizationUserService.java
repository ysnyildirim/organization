/*
 * Copyright (c) 2022. Tüm hakları Yasin Yıldırım'a aittir.
 */

package com.yil.organization.service;

import com.yil.organization.dto.OrganizationPersonRequest;
import com.yil.organization.dto.OrganizationUserDto;
import com.yil.organization.dto.OrganizationUserRequest;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationUserNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.Organization;
import com.yil.organization.model.OrganizationUser;
import com.yil.organization.repository.OrganizationDao;
import com.yil.organization.repository.OrganizationUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class OrganizationUserService {
    private final OrganizationUserDao organizationUserDao;
    private final OrganizationDao organizationDao;

    public static OrganizationUserDto toDto(OrganizationUser organizationUser) {
        return OrganizationUserDto
                .builder()
                .organizationId(organizationUser.getId().getOrganizationId())
                .userId(organizationUser.getId().getUserId())
                .manager(organizationUser.getManager())
                .build();
    }

    @Transactional
    public void delete(long authenticatedUserId, long organizationId, long userId) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!hasPermission(organizationId, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        organizationUserDao.deleteById(OrganizationUser.Pk.builder().userId(userId).organizationId(organizationId).build());
    }

    @Transactional(readOnly = true)
    public Page<OrganizationUser> findAllByOrganizationId(Pageable pageable, Long organizationId) {
        return organizationUserDao.findAllById_OrganizationId(pageable, organizationId);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationUser> findAllByUserId(Pageable pageable, Long userId) {
        return organizationUserDao.findAllById_UserId(pageable, userId);
    }

    @Transactional
    public OrganizationUser save(Long organizationId, Long userId, Long authenticatedUserId, OrganizationUserRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!hasPermission(organizationId, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        OrganizationUser entity = new OrganizationUser();
        entity.setId(OrganizationUser.Pk.builder()
                .organizationId(organizationId)
                .userId(userId)
                .build());
        entity.setManager(request.getManager());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = organizationUserDao.save(entity);
        return entity;
    }

    @Transactional
    public boolean hasPermission(Long organizationId, Long userId) throws OrganizationNotFoundException {
        Organization organization = organizationDao.findById(organizationId).orElseThrow(OrganizationNotFoundException::new);
        return hasPermission(organization, userId);
    }

    @Transactional
    public boolean hasPermission(Organization organization, long userId) throws OrganizationNotFoundException {
        if (organization.getCreatedUserId().equals(userId))
            return true;
        if (organizationUserDao.existsAllById_UserIdAndId_OrganizationIdAndManagerTrue(userId, organization.getId()))
            return true;
        if (organization.getParentId() != null)
            return hasPermission(organization.getParentId(), userId);
        return false;
    }

    @Transactional
    public void replace(Long authenticatedUserId, Long organizationId, Long userId, OrganizationUserRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager, OrganizationUserNotFoundException {
        if (!hasPermission(organizationId, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        OrganizationUser organizationUser = organizationUserDao.findById(OrganizationUser.Pk.builder().userId(userId).organizationId(organizationId).build()).orElseThrow(OrganizationUserNotFoundException::new);
        organizationUser.setManager(request.getManager());
        organizationUser.setLastModifyUserId(authenticatedUserId);
        organizationUser.setLastModifyDate(new Date());
        organizationUserDao.save(organizationUser);
    }
}
