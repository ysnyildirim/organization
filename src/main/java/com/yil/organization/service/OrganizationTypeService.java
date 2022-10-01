package com.yil.organization.service;

import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.repository.OrganizationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationTypeService {

    private final OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    public OrganizationTypeService(OrganizationTypeRepository organizationTypeRepository) {
        this.organizationTypeRepository = organizationTypeRepository;
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
        return organizationTypeRepository.existsById(id);
    }

    public OrganizationType save(OrganizationType organizationType) {
        return organizationTypeRepository.save(organizationType);
    }

    public OrganizationType findById(Long id) throws OrganizationTypeNotFoundException {
        return organizationTypeRepository.findById(id).orElseThrow(OrganizationTypeNotFoundException::new);
    }

    public void deleteById(long id) {
        organizationTypeRepository.deleteById(id);
    }

    public List<OrganizationType> findAll() {
        return organizationTypeRepository.findAll();
    }

}
