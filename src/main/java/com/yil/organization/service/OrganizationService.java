package com.yil.organization.service;

import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.model.Organization;
import com.yil.organization.repository.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    public static Organization bireysel;
    public static Organization kurumsal;
    private final OrganizationDao organizationDao;

    @Autowired
    public OrganizationService(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public static OrganizationDto toDto(Organization organization) throws NullPointerException {
        if (organization == null)
            throw new NullPointerException("Organization is null");
        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setOrganizationTypeId(organization.getOrganizationTypeId());
        dto.setParentId(organization.getParentId());
        return dto;
    }

    public Organization findById(Long id) throws OrganizationNotFoundException {
        return organizationDao.findById(id).orElseThrow(OrganizationNotFoundException::new);
    }

    public boolean existsById(Long id) {
        return organizationDao.existsById(id);
    }

    public Organization save(Organization organization) {
        return organizationDao.save(organization);
    }

    public void deleteById(long id) {
        organizationDao.deleteById(id);
    }

    public void delete(Organization entity) {
        organizationDao.delete(entity);
    }

    public Page<Organization> findAll(Pageable pageable) {
        return organizationDao.findAll(pageable);
    }
}
