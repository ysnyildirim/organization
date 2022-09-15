package com.yil.organization.service;

import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.model.Organization;
import com.yil.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
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
        return organizationRepository.findById(id).orElseThrow(OrganizationNotFoundException::new);
    }


    public boolean existsById(Long id) {
        return organizationRepository.existsById(id);
    }

    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteById(long id) {
        organizationRepository.deleteById(id);
    }

    public void delete(Organization entity) {
        organizationRepository.delete(entity);
    }

    public Page<Organization> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }
}
