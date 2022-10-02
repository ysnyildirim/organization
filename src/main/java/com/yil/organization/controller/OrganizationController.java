package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.Mapper;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationDto;
import com.yil.organization.dto.CreateOrganizationResponse;
import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.exception.YouDoNotHavePermissionException;
import com.yil.organization.model.Organization;
import com.yil.organization.service.OrganizationPersonService;
import com.yil.organization.service.OrganizationService;
import com.yil.organization.service.OrganizationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/org/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OrganizationTypeService organizationTypeService;
    private final Mapper<Organization, OrganizationDto> mapper = new Mapper<>(OrganizationService::toDto);
    private final OrganizationPersonService organizationPersonService;


    @GetMapping
    public ResponseEntity<PageDto<OrganizationDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        PageDto<OrganizationDto> pageDto = mapper.map(organizationService.findAll(pageable));
        return ResponseEntity.ok(pageDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationDto> findById(@PathVariable Long id) throws OrganizationNotFoundException {
        OrganizationDto dto = mapper.map(organizationService.findById(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateOrganizationResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                                             @Valid @RequestBody CreateOrganizationDto dto) throws OrganizationNotFoundException, OrganizationTypeNotFoundException, YouDoNotHavePermissionException {
        if (dto.getParentId() != null && !organizationService.existsById(dto.getParentId()))
            throw new OrganizationNotFoundException();
        if (!organizationTypeService.existsById(dto.getOrganizationTypeId()))
            throw new OrganizationTypeNotFoundException();
        Organization organization = new Organization();
        organization.setOrganizationTypeId(dto.getOrganizationTypeId());
        organization.setParentId(dto.getParentId());
        organization.setCreatedUserId(authenticatedOrganizationId);
        organization.setCreatedTime(new Date());
        organization = organizationService.save(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateOrganizationResponse.builder().id(organization.getId()).build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @RequestHeader(value = ApiConstant.AUTHENTICATED_PERSON_ID) Long personId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationDto dto) throws OrganizationTypeNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        Organization organization = organizationService.findById(id);
        if (organizationPersonService.existsByOrganizationIdAndPersonIdAndManagerTrue(organization.getId(), personId))
            throw new YouAreNotOrganizationManager();
        if (dto.getParentId() != null && !organizationService.existsById(dto.getParentId()))
            throw new OrganizationNotFoundException();
        if (!organizationTypeService.existsById(dto.getOrganizationTypeId()))
            throw new OrganizationTypeNotFoundException();
        organization.setOrganizationTypeId(dto.getOrganizationTypeId());
        organization.setParentId(dto.getParentId());
        organization = organizationService.save(organization);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @RequestHeader(value = ApiConstant.AUTHENTICATED_PERSON_ID) Long personId,
                                         @PathVariable Long id) throws OrganizationNotFoundException, YouDoNotHavePermissionException, YouAreNotOrganizationManager {
        Organization organization = organizationService.findById(id);
        if (organizationPersonService.existsByOrganizationIdAndPersonIdAndManagerTrue(organization.getId(), personId))
            throw new YouAreNotOrganizationManager();
        organizationService.delete(organization);
        return ResponseEntity.ok("Organization deleted.");
    }


}
