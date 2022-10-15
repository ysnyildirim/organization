package com.yil.organization.service;

import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.repository.OrganizationTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationTypeService {

    public static OrganizationType gercekKisi;
    public static OrganizationType tuzelKisi;
    private final OrganizationTypeDao organizationTypeDao;

    @Autowired
    public OrganizationTypeService(OrganizationTypeDao organizationTypeDao) {
        this.organizationTypeDao = organizationTypeDao;
    }

    public static OrganizationTypeDto toDto(OrganizationType f) {
        if (f == null)
            throw new NullPointerException("Organization type is null");
        OrganizationTypeDto dto = new OrganizationTypeDto();
        dto.setName(f.getName());
        dto.setId(f.getId());
        return dto;
    }

    public boolean existsById(Long id) {
        return organizationTypeDao.existsById(id);
    }

    public OrganizationType save(OrganizationType organizationType) {
        return organizationTypeDao.save(organizationType);
    }

    public OrganizationType findById(Long id) throws OrganizationTypeNotFoundException {
        return organizationTypeDao.findById(id).orElseThrow(OrganizationTypeNotFoundException::new);
    }

    public void deleteById(long id) {
        organizationTypeDao.deleteById(id);
    }

    public List<OrganizationType> findAll() {
        return organizationTypeDao.findAll();
    }
}
