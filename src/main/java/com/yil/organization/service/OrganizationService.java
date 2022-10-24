package com.yil.organization.service;

import com.yil.organization.dto.CreateOrganizationResponse;
import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.dto.OrganizationRequest;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.Organization;
import com.yil.organization.repository.OrganizationCompanyDao;
import com.yil.organization.repository.OrganizationDao;
import com.yil.organization.repository.OrganizationPersonDao;
import com.yil.organization.repository.OrganizationUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class OrganizationService {
    public static Organization bireysel;
    public static Organization kurumsal;
    private final OrganizationDao organizationDao;
    private final OrganizationUserDao organizationUserDao;
    private final OrganizationPersonDao organizationPersonDao;
    private final OrganizationCompanyDao organizationCompanyDao;
    private final OrganizationTypeService organizationTypeService;
    private final OrganizationUserService organizationUserService;

    public static OrganizationDto toDto(Organization organization) throws NullPointerException {
        return OrganizationDto
                .builder()
                .id(organization.getId())
                .parentId(organization.getParentId())
                .name(organization.getName())
                .description(organization.getDescription())
                .organizationTypeId(organization.getOrganizationTypeId())
                .build();
    }

    @Transactional
    public void deleteById(long id, long authenticatedUserId) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!organizationUserService.hasPermission(id, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        organizationDao.deleteById(id);
        organizationUserDao.deleteAllById_OrganizationId(id);
        organizationPersonDao.deleteAllByOrganizationId(id);
        organizationCompanyDao.deleteAllByOrganizationId(id);
    }

    @Transactional(readOnly = true)
    public Page<Organization> findAll(  Pageable pageable) {
        return organizationDao.findAll(pageable);
    }

    @Transactional
    public CreateOrganizationResponse save(Long userId, OrganizationRequest request) throws OrganizationNotFoundException, OrganizationTypeNotFoundException, YouAreNotOrganizationManager {
        if (request.getParentId() != null && !organizationUserService.hasPermission(request.getParentId(), userId))  //parent seçebilmek için yetkili olsun
            throw new YouAreNotOrganizationManager();
        if (!organizationTypeService.existsById(request.getOrganizationTypeId()))
            throw new OrganizationTypeNotFoundException();
        Organization organization = new Organization();
        organization.setOrganizationTypeId(request.getOrganizationTypeId());
        organization.setParentId(request.getParentId());
        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setCreatedUserId(userId);
        organization.setCreatedTime(new Date());
        organization = organizationDao.save(organization);
        return CreateOrganizationResponse.builder().id(organization.getId()).build();
    }

    @Transactional
    public void replace(Long authenticatedUserId, Long id, OrganizationRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager, OrganizationTypeNotFoundException {
        Organization organization = findById(id);
        if (!organizationUserService.hasPermission(organization, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        if (request.getParentId() != null && !organizationUserService.hasPermission(request.getParentId(), authenticatedUserId)) //parent seçebilmek için yetkili olsun
            throw new YouAreNotOrganizationManager();
        if (!organizationTypeService.existsById(request.getOrganizationTypeId()))
            throw new OrganizationTypeNotFoundException();
        organization.setOrganizationTypeId(request.getOrganizationTypeId());
        organization.setParentId(request.getParentId());
        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setLastModifyDate(new Date());
        organization.setLastModifyUserId(authenticatedUserId);
        organizationDao.save(organization);
    }

    @Transactional(readOnly = true)
    public Organization findById(Long id) throws OrganizationNotFoundException {
        return organizationDao.findById(id).orElseThrow(OrganizationNotFoundException::new);
    }
}
