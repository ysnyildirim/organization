package com.yil.organization.service;

import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.dto.OrganizationPersonRequest;
import com.yil.organization.dto.OrganizationPersonResponse;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationPersonNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.repository.OrganizationPersonDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class OrganizationPersonService {
    private final OrganizationPersonDao organizationPersonDao;
    private final OrganizationUserService organizationUserService;

    public static OrganizationPersonDto toDto(OrganizationPerson organizationPerson) {
        OrganizationPersonDto dto = new OrganizationPersonDto();
        dto.setId(organizationPerson.getId());
        dto.setPersonId(organizationPerson.getPersonId());
        dto.setOrganizationId(organizationPerson.getOrganizationId());
        dto.setFromDate(organizationPerson.getFromDate());
        dto.setToDate(organizationPerson.getToDate());
        return dto;
    }

    @Transactional(readOnly = true)
    public Page<OrganizationPerson> findAllByOrganizationId(Pageable pageable, Long organizationId) {
        return organizationPersonDao.findAllByOrganizationId(pageable, organizationId);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationPerson> findAllByPersonId(Pageable pageable, Long personId) {
        return organizationPersonDao.findAllByPersonId(pageable, personId);
    }

    @Transactional
    public void replace(Long authenticatedUserId, Long id, OrganizationPersonRequest request) throws OrganizationPersonNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        OrganizationPerson entity = findById(id);
        if (!organizationUserService.hasPermission(entity.getOrganizationId(), authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        entity.setFromDate(request.getFromDate());
        entity.setToDate(request.getToDate());
        entity.setLastModifyDate(new Date());
        entity.setLastModifyUserId(authenticatedUserId);
        organizationPersonDao.save(entity);
    }

    @Transactional(readOnly = true)
    public OrganizationPerson findById(Long id) throws OrganizationPersonNotFoundException {
        return organizationPersonDao.findById(id).orElseThrow(OrganizationPersonNotFoundException::new);
    }

    @Transactional
    public void deleteById(Long authenticatedUserId, Long id) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!organizationUserService.hasPermission(id, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        organizationPersonDao.deleteById(id);
    }

    @Transactional
    public OrganizationPersonResponse save(Long authenticatedUserId, Long organizationId, Long personId, OrganizationPersonRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!organizationUserService.hasPermission(organizationId, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        OrganizationPerson entity = new OrganizationPerson();
        entity.setOrganizationId(organizationId);
        entity.setPersonId(personId);
        entity.setFromDate(request.getFromDate());
        entity.setToDate(request.getToDate());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = organizationPersonDao.save(entity);
        return OrganizationPersonResponse.builder().id(entity.getId()).build();
    }
}
