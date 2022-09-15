package com.yil.organization.service;

import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.repository.OrganizationPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class OrganizationPersonService {
    private final OrganizationPersonRepository organizationPersonRepository;

    @Autowired
    public OrganizationPersonService(OrganizationPersonRepository organizationPersonRepository) {
        this.organizationPersonRepository = organizationPersonRepository;
    }

    public static OrganizationPersonDto toDto(OrganizationPerson organizationPerson) {
        if (organizationPerson == null)
            throw new NullPointerException("Organization person is null");
        OrganizationPersonDto dto = new OrganizationPersonDto();
        dto.setId(organizationPerson.getId());
        dto.setPersonId(organizationPerson.getPersonId());
        dto.setOrganizationId(organizationPerson.getOrganizationId());
        return dto;
    }

    public OrganizationPerson findById(Long id) throws EntityNotFoundException {
        return organizationPersonRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public OrganizationPerson save(OrganizationPerson person) {
        return organizationPersonRepository.save(person);
    }

    public Page<OrganizationPerson> findAllByAndOrganizationIdAndDeletedTimeIsNull(Pageable pageable, Long organizationId) {
        return organizationPersonRepository.findAllByAndOrganizationIdAndDeletedTimeIsNull(pageable, organizationId);
    }


    public boolean existsByOrganizationIdAndPersonIdAndManagerTrue(long organizationId,long personId) {
        return organizationPersonRepository.existsByOrganizationIdAndPersonIdAndManagerTrue(organizationId,personId);
    }
}
