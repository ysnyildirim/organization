package com.yil.organization.service;

import com.yil.organization.dto.OrganizationCompanyDto;
import com.yil.organization.dto.OrganizationCompanyRequest;
import com.yil.organization.dto.OrganizationCompanyResponse;
import com.yil.organization.exception.OrganizationCompanyNotFoundException;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.OrganizationCompany;
import com.yil.organization.repository.OrganizationCompanyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrganizationCompanyService {
    private final OrganizationCompanyDao organizationCompanyDao;
    private final OrganizationUserService organizationUserService;

    public static OrganizationCompanyDto toDto(OrganizationCompany organizationCompany) {
        return OrganizationCompanyDto
                .builder()
                .id(organizationCompany.getId())
                .companyId(organizationCompany.getCompanyId())
                .organizationId(organizationCompany.getOrganizationId())
                .fromDate(organizationCompany.getFromDate())
                .toDate(organizationCompany.getToDate())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<OrganizationCompany> findAllByAndOrganizationId(Pageable pageable, Long organizationId) {
        return organizationCompanyDao.findAllByOrganizationId(pageable, organizationId);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationCompany> findAllByAndCompanyId(Pageable pageable, Long companyId) {
        return organizationCompanyDao.findAllByCompanyId(pageable, companyId);
    }

    @Transactional
    public void replace(Long authenticatedUserId, Long id, OrganizationCompanyRequest request) throws OrganizationCompanyNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        OrganizationCompany organizationCompany = organizationCompanyDao.findById(id).orElseThrow(OrganizationCompanyNotFoundException::new);
        if (!organizationUserService.hasPermission(organizationCompany.getOrganizationId(), authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        organizationCompany.setToDate(request.getToDate());
        organizationCompany.setFromDate(request.getFromDate());
        organizationCompany.setLastModifyDate(new Date());
        organizationCompany.setLastModifyUserId(authenticatedUserId);
        organizationCompanyDao.save(organizationCompany);
    }

    @Transactional
    public void delete(Long authenticatedUserId,Long id) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!organizationUserService.hasPermission(id, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        organizationCompanyDao.deleteById(id);
    }

    @Transactional
    public OrganizationCompanyResponse save(Long authenticatedUserId, Long organizationId, Long companyId, OrganizationCompanyRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        if (!organizationUserService.hasPermission(organizationId, authenticatedUserId))
            throw new YouAreNotOrganizationManager();
        OrganizationCompany entity = new OrganizationCompany();
        entity.setOrganizationId(organizationId);
        entity.setCompanyId(companyId);
        entity.setFromDate(request.getFromDate());
        entity.setToDate(request.getToDate());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = organizationCompanyDao.save(entity);
        return OrganizationCompanyResponse.builder().id(entity.getId()).build();
    }
}
