package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.Mapper;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationPersonDto;
import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.exception.OrganizationPersonNotFoundException;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.service.OrganizationPersonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/org/v1/organizations/{organizationId}/persons")
public class OrganizationPersonController {

    private final OrganizationPersonService organizationPersonService;
    private final Mapper<OrganizationPerson, OrganizationPersonDto> mapper = new Mapper<>(OrganizationPersonService::toDto);

    @GetMapping
    public ResponseEntity<PageDto<OrganizationPersonDto>> findAll(
            @PathVariable Long organizationId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        Page<OrganizationPerson> organizationPage = organizationPersonService.findAllByOrganizationId(pageable, organizationId);
        return ResponseEntity.ok(mapper.map(organizationPage));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationPersonDto> findById(
            @PathVariable Long organizationId,
            @PathVariable Long id) throws OrganizationPersonNotFoundException {
        OrganizationPerson organization = organizationPersonService.findByIdAndOrganizationId(id, organizationId);
        return ResponseEntity.ok(mapper.map(organization));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                 @PathVariable Long organizationId,
                                 @Valid @RequestBody CreateOrganizationPersonDto dto) {
        OrganizationPerson entity = new OrganizationPerson();
        entity.setOrganizationId(organizationId);
        entity.setPersonId(dto.getPersonId());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = organizationPersonService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long organizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateOrganizationPersonDto dto) throws OrganizationPersonNotFoundException {
        OrganizationPerson entity = organizationPersonService.findByIdAndOrganizationId(id, organizationId);
        entity.setOrganizationId(organizationId);
        entity.setPersonId(dto.getPersonId());
        entity = organizationPersonService.save(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long organizationId,
                                         @PathVariable Long id) {
        organizationPersonService.deleteByIdAndOrganizationId(id, organizationId);
        return ResponseEntity.ok("Person email deleted.");
    }


}
