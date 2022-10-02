package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.Mapper;
import com.yil.organization.dto.CreateOrganizationTypeDto;
import com.yil.organization.dto.OrganizationTypeDto;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.model.OrganizationType;
import com.yil.organization.service.OrganizationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/org/v1/organization-types")
public class OrganizationTypeController {

    private final OrganizationTypeService organizationTypeService;
    private final Mapper<OrganizationType, OrganizationTypeDto> mapper = new Mapper<>(OrganizationTypeService::toDto);

    @GetMapping
    public ResponseEntity<List<OrganizationTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.map(organizationTypeService.findAll()));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationTypeDto> findById(@PathVariable Long id) throws OrganizationTypeNotFoundException {
        return ResponseEntity.ok(mapper.map(organizationTypeService.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                 @Valid @RequestBody CreateOrganizationTypeDto dto) {
        OrganizationType organizationType = new OrganizationType();
        organizationType.setName(dto.getName());
        organizationType.setCreatedTime(new Date());
        organizationType.setCreatedUserId(authenticatedUserId);
        organizationType = organizationTypeService.save(organizationType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationTypeDto dto) throws OrganizationTypeNotFoundException {
        OrganizationType organizationType = organizationTypeService.findById(id);
        organizationType.setName(dto.getName());
        organizationType = organizationTypeService.save(organizationType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        organizationTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
