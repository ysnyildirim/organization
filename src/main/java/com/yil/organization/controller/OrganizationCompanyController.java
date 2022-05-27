package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationCompanyDto;
import com.yil.organization.dto.OrganizationCompanyDto;
import com.yil.organization.model.OrganizationCompany;
import com.yil.organization.service.OrganizationCompanyService;
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
@RequestMapping(value = "v1/organizations/{organizationId}/companies")
public class OrganizationCompanyController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final OrganizationCompanyService organizationCompanyService;

    @Autowired
    public OrganizationCompanyController(OrganizationCompanyService organizationCompanyService) {
        this.organizationCompanyService = organizationCompanyService;
    }

    @GetMapping
    public ResponseEntity<PageDto<OrganizationCompanyDto>> findAll(
            @PathVariable Long organizationId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<OrganizationCompany> organizationPage = organizationCompanyService.findAllByAndOrganizationIdAndDeletedTimeIsNull(pageable, organizationId);
            PageDto<OrganizationCompanyDto> pageDto = PageDto.toDto(organizationPage, OrganizationCompanyService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationCompanyDto> findById(
            @PathVariable Long organizationId,
            @PathVariable Long id) {
        try {
            OrganizationCompany organization;
            try {
                organization = organizationCompanyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!organization.getCompanyId().equals(organizationId))
                return ResponseEntity.notFound().build();
            OrganizationCompanyDto dto = OrganizationCompanyService.toDto(organization);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationCompanyId,
                                 @PathVariable Long organizationId,
                                 @Valid @RequestBody CreateOrganizationCompanyDto dto) {
        try {
            OrganizationCompany entity = new OrganizationCompany();
            entity.setOrganizationId(organizationId);
            entity.setCompanyId(dto.getCompanyId());
            entity.setCreatedUserId(authenticatedOrganizationCompanyId);
            entity.setCreatedTime(new Date());
            entity = organizationCompanyService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationCompanyId,
                                  @PathVariable Long organizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationCompanyDto dto) {
        try {
            OrganizationCompany entity = null;
            try {
                entity = organizationCompanyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getCompanyId().equals(organizationId))
                return ResponseEntity.notFound().build();
            entity.setOrganizationId(organizationId);
            entity.setCompanyId(dto.getCompanyId());
            entity = organizationCompanyService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationCompanyId,
                                         @PathVariable Long organizationId,
                                         @PathVariable Long id) {
        try {
            OrganizationCompany entity;
            try {
                entity = organizationCompanyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getCompanyId().equals(organizationId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedOrganizationCompanyId);
            entity.setDeletedTime(new Date());
            entity = organizationCompanyService.save(entity);
            return ResponseEntity.ok("Company email deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
