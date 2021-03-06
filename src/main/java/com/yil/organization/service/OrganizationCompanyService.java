package com.yil.organization.service;

import com.yil.organization.dto.OrganizationCompanyDto;
import com.yil.organization.model.OrganizationCompany;
import com.yil.organization.repository.OrganizationCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class OrganizationCompanyService {
    private final OrganizationCompanyRepository organizationCompanyRepository;

    @Autowired
    public OrganizationCompanyService(OrganizationCompanyRepository organizationCompanyRepository) {
        this.organizationCompanyRepository = organizationCompanyRepository;
    }

    public static OrganizationCompanyDto toDto(OrganizationCompany organizationCompany) {
        if (organizationCompany == null)
            throw new NullPointerException("Organization company is null");
        OrganizationCompanyDto dto = new OrganizationCompanyDto();
        dto.setId(organizationCompany.getId());
        dto.setCompanyId(organizationCompany.getCompanyId());
        dto.setOrganizationId(organizationCompany.getOrganizationId());
        return dto;
    }

    public OrganizationCompany findById(Long id) throws EntityNotFoundException {
        return organizationCompanyRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public OrganizationCompany save(OrganizationCompany company) {
        return organizationCompanyRepository.save(company);
    }

    public Page<OrganizationCompany> findAllByAndOrganizationIdAndDeletedTimeIsNull(Pageable pageable, Long organizationId) {
        return organizationCompanyRepository.findAllByAndOrganizationIdAndDeletedTimeIsNull(pageable, organizationId);
    }
}
