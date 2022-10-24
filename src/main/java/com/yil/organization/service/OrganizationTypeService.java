package com.yil.organization.service;

import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.repository.OrganizationTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
        return OrganizationTypeDto.builder()
                .id(f.getId())
                .name(f.getName())
                .real(f.getReal())
                .build();
    }

    public boolean existsById(Integer id) {
        return organizationTypeDao.existsById(id);
    }

    @CacheEvict(value = "organization-type", allEntries = true)
    public OrganizationType save(OrganizationType organizationType) {
        return organizationTypeDao.save(organizationType);
    }

    public OrganizationType findById(Integer id) throws OrganizationTypeNotFoundException {
        return organizationTypeDao.findById(id).orElseThrow(OrganizationTypeNotFoundException::new);
    }

    @CacheEvict(value = "organization-type", allEntries = true)
    public void deleteById(Integer id) {
        organizationTypeDao.deleteById(id);
    }

    @Cacheable("organization-type")
    public List<OrganizationType> findAll() {
        return organizationTypeDao.findAll();
    }
}
