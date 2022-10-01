package com.yil.organization.service;

import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.exception.OrganizationPersonNotFoundException;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.repository.OrganizationPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrganizationPersonService {
    private final OrganizationPersonRepository organizationPersonRepository;

    public static OrganizationPersonDto toDto(OrganizationPerson organizationPerson) {
        if (organizationPerson == null)
            throw new NullPointerException("Organization person is null");
        OrganizationPersonDto dto = new OrganizationPersonDto();
        dto.setId(organizationPerson.getId());
        dto.setPersonId(organizationPerson.getPersonId());
        dto.setOrganizationId(organizationPerson.getOrganizationId());
        return dto;
    }

    public OrganizationPerson findById(Long id) throws OrganizationPersonNotFoundException {
        return organizationPersonRepository.findById(id).orElseThrow(OrganizationPersonNotFoundException::new);
    }

    public OrganizationPerson save(OrganizationPerson person) {
        return organizationPersonRepository.save(person);
    }

    public void deleteByIdAndOrganizationId(long id, long organizationId) {
        organizationPersonRepository.deleteByIdAndOrganizationId(id, organizationId);
    }

    public Page<OrganizationPerson> findAllByOrganizationId(Pageable pageable, Long organizationId) {
        return organizationPersonRepository.findAllByOrganizationId(pageable, organizationId);
    }

    public OrganizationPerson findByIdAndOrganizationId(long id, long organizationId) throws OrganizationPersonNotFoundException {
        return organizationPersonRepository.findByIdAndOrganizationId(id, organizationId).orElseThrow(OrganizationPersonNotFoundException::new);
    }


    public boolean existsByOrganizationIdAndPersonIdAndManagerTrue(long organizationId, long personId) {
        return organizationPersonRepository.existsByOrganizationIdAndPersonIdAndManagerTrue(organizationId, personId);
    }
}
