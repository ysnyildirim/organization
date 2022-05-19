package com.yil.organization.controller;

import com.yil.organization.base.ApiHeaders;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationDto;
import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.model.Organization;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.service.OrganizationService;
import com.yil.organization.service.OrganizationTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final OrganizationService organizationService;
    private final OrganizationTypeService organizationTypeService;

    @Autowired
    public OrganizationController(OrganizationService organizationService, OrganizationTypeService organizationTypeService) {
        this.organizationService = organizationService;
        this.organizationTypeService = organizationTypeService;
    }

    @GetMapping
    public ResponseEntity<PageDto<OrganizationDto>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<Organization> organizationPage = organizationService.findAllByDeletedTimeIsNull(pageable);
            PageDto<OrganizationDto> pageDto = PageDto.toDto(organizationPage, OrganizationService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationDto> findById(@PathVariable Long id) {
        try {
            Organization organization;
            try {
                organization = organizationService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            OrganizationDto dto = OrganizationService.toDto(organization);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                 @Valid @RequestBody CreateOrganizationDto dto) {
        try {
            OrganizationType organizationType = null;
            try {
                organizationType = organizationTypeService.findById(dto.getOrganizationTypeId());
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            Organization parent = null;
            if (dto.getParentId() != null) {
                try {
                    parent = organizationService.findById(dto.getParentId());
                } catch (EntityNotFoundException entityNotFoundException) {
                    return ResponseEntity.notFound().build();
                }
            }
            Organization organization = new Organization();
            organization.setOrganizationTypeId(organizationType.getId());
            if (parent != null)
                organization.setParentId(parent.getId());
            organization.setCreatedUserId(authenticatedOrganizationId);
            organization.setCreatedTime(new Date());
            organization = organizationService.save(organization);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationDto dto) {
        try {
            OrganizationType organizationType = null;
            try {
                organizationType = organizationTypeService.findById(dto.getOrganizationTypeId());
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            Organization parent = null;
            if (dto.getParentId() != null) {
                try {
                    parent = organizationService.findById(dto.getParentId());
                } catch (EntityNotFoundException entityNotFoundException) {
                    return ResponseEntity.notFound().build();
                }
            }
            Organization organization = new Organization();
            organization.setOrganizationTypeId(organizationType.getId());
            if (parent != null)
                organization.setParentId(parent.getId());
            organization = organizationService.save(organization);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                         @PathVariable Long id) {
        try {
            Organization organization;
            try {
                organization = organizationService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            organization.setDeletedUserId(authenticatedOrganizationId);
            organization.setDeletedTime(new Date());
            organizationService.save(organization);
            return ResponseEntity.ok("Organization deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
