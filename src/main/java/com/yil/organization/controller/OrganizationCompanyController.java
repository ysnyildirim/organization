package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.OrganizationCompanyDto;
import com.yil.organization.dto.OrganizationCompanyRequest;
import com.yil.organization.exception.OrganizationCompanyNotFoundException;
import com.yil.organization.model.OrganizationCompany;
import com.yil.organization.service.OrganizationCompanyService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/org/v1/organizations/{organizationId}/companies")
public class OrganizationCompanyController {
    private final OrganizationCompanyService organizationCompanyService;

    @GetMapping
    public ResponseEntity<PageDto<OrganizationCompanyDto>> findAll(
            @PathVariable Long organizationId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        Page<OrganizationCompany> organizationPage = organizationCompanyService.findAllByAndOrganizationId(pageable, organizationId);
        PageDto<OrganizationCompanyDto> pageDto = PageDto.toDto(organizationPage, OrganizationCompanyService::toDto);
        return ResponseEntity.ok(pageDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationCompanyDto> findById(
            @PathVariable Long organizationId,
            @PathVariable Long id) throws OrganizationCompanyNotFoundException {
        OrganizationCompany organization = organizationCompanyService.findByIdAndAndOrganizationId(id, organizationId);
        OrganizationCompanyDto dto = OrganizationCompanyService.toDto(organization);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                 @PathVariable Long organizationId,
                                 @Valid @RequestBody OrganizationCompanyRequest request) {
        OrganizationCompany entity = new OrganizationCompany();
        entity.setOrganizationId(organizationId);
        entity.setCompanyId(request.getCompanyId());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = organizationCompanyService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long organizationId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody OrganizationCompanyRequest dto) throws OrganizationCompanyNotFoundException {
        OrganizationCompany entity = organizationCompanyService.findByIdAndAndOrganizationId(id, organizationId);
        entity.setOrganizationId(organizationId);
        entity.setCompanyId(dto.getCompanyId());
        entity = organizationCompanyService.save(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long organizationId,
                                         @PathVariable Long id) {
        organizationCompanyService.deleteByIdAndOrganizationId(id, organizationId);
        return ResponseEntity.ok("Deleted");
    }
}
