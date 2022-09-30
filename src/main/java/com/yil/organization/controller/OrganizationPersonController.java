package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationPersonDto;
import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.service.OrganizationPersonService;
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
@RequestMapping(value = "/api/org/v1/organizations/{organizationId}/persons")
public class OrganizationPersonController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final OrganizationPersonService organizationPersonService;

    @Autowired
    public OrganizationPersonController(OrganizationPersonService organizationPersonService) {
        this.organizationPersonService = organizationPersonService;
    }

    @GetMapping
    public ResponseEntity<PageDto<OrganizationPersonDto>> findAll(
            @PathVariable Long organizationId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<OrganizationPerson> organizationPage = organizationPersonService.findAllByAndOrganizationIdAndDeletedTimeIsNull(pageable, organizationId);
            PageDto<OrganizationPersonDto> pageDto = PageDto.toDto(organizationPage, OrganizationPersonService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationPersonDto> findById(
            @PathVariable Long organizationId,
            @PathVariable Long id) {
        try {
            OrganizationPerson organization;
            try {
                organization = organizationPersonService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!organization.getPersonId().equals(organizationId))
                return ResponseEntity.notFound().build();
            OrganizationPersonDto dto = OrganizationPersonService.toDto(organization);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationPersonId,
                                 @PathVariable Long organizationId,
                                 @Valid @RequestBody CreateOrganizationPersonDto dto) {
        try {
            OrganizationPerson entity = new OrganizationPerson();
            entity.setOrganizationId(organizationId);
            entity.setPersonId(dto.getPersonId());
            entity.setCreatedUserId(authenticatedOrganizationPersonId);
            entity.setCreatedTime(new Date());
            entity = organizationPersonService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationPersonId,
                                  @PathVariable Long organizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationPersonDto dto) {
        try {
            OrganizationPerson entity = null;
            try {
                entity = organizationPersonService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getPersonId().equals(organizationId))
                return ResponseEntity.notFound().build();
            entity.setOrganizationId(organizationId);
            entity.setPersonId(dto.getPersonId());
            entity = organizationPersonService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedOrganizationPersonId,
                                         @PathVariable Long organizationId,
                                         @PathVariable Long id) {
        try {
            OrganizationPerson entity;
            try {
                entity = organizationPersonService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getPersonId().equals(organizationId))
                return ResponseEntity.notFound().build();
            entity = organizationPersonService.save(entity);
            return ResponseEntity.ok("Person email deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
