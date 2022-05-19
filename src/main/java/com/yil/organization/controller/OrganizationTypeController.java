package com.yil.organization.controller;

import com.yil.organization.base.ApiHeaders;
import com.yil.organization.dto.CreateOrganizationTypeDto;
import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.service.OrganizationTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/organization-types")
public class OrganizationTypeController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final OrganizationTypeService organizationTypeService;

    @Autowired
    public OrganizationTypeController(OrganizationTypeService organizationTypeService) {
        this.organizationTypeService = organizationTypeService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationTypeDto>> findAll() {
        try {
            List<OrganizationType> data = organizationTypeService.findAllByDeletedTimeIsNull();
            List<OrganizationTypeDto> dtoData = new ArrayList<>();
            data.forEach(f -> {
                dtoData.add(OrganizationTypeService.toDto(f));
            });
            return ResponseEntity.ok(dtoData);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationTypeDto> findById(@PathVariable Long id) {
        try {
            OrganizationType organizationType = organizationTypeService.findById(id);
            OrganizationTypeDto dto = OrganizationTypeService.toDto(organizationType);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                 @Valid @RequestBody CreateOrganizationTypeDto dto) {
        try {
            OrganizationType organizationType = new OrganizationType();
            organizationType.setName(dto.getName());
            organizationType.setCreatedUserId(authenticatedOrganizationId);
            organizationType.setCreatedTime(new Date());
            organizationType = organizationTypeService.save(organizationType);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedOrganizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationTypeDto dto) {
        try {
            OrganizationType organizationType;
            try {
                organizationType = organizationTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            organizationType.setName(dto.getName());
            organizationType = organizationTypeService.save(organizationType);
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
            OrganizationType organizationType;
            try {
                organizationType = organizationTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            organizationType.setDeletedUserId(authenticatedOrganizationId);
            organizationType.setDeletedTime(new Date());
            organizationTypeService.save(organizationType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

}
