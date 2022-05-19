package com.yil.organization.service;

import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.repository.OrganizationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public OrganizationType save(OrganizationType organizationType) {
        return organizationTypeRepository.save(organizationType);
    }

    public OrganizationType findById(Long id) throws EntityNotFoundException {
        return organizationTypeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
    }

    public List<OrganizationType> findAllByDeletedTimeIsNull() {
        return organizationTypeRepository.findAllByDeletedTimeIsNull();
    }

}
