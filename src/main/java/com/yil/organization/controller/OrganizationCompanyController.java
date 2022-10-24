package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.OrganizationCompanyDto;
import com.yil.organization.dto.OrganizationCompanyRequest;
import com.yil.organization.dto.OrganizationCompanyResponse;
import com.yil.organization.exception.OrganizationCompanyNotFoundException;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.OrganizationCompany;
import com.yil.organization.service.OrganizationCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/org/v1")
public class OrganizationCompanyController {
    private final OrganizationCompanyService organizationCompanyService;

    @Operation(summary = "Şirketin organizasyonlarını getirir.")
    @GetMapping("/companies/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationCompanyDto>> findAllByCompanyId(
            @PathVariable Long companyId,
            @PageableDefault Pageable pageable) {
        Page<OrganizationCompany> organizationPage = organizationCompanyService.findAllByAndCompanyId(pageable, companyId);
        PageDto<OrganizationCompanyDto> pageDto = PageDto.toDto(organizationPage, OrganizationCompanyService::toDto);
        return ResponseEntity.ok(pageDto);
    }

    @Operation(summary = "Organizasyondaki şirketleri getirir.")
    @GetMapping("/{organizationId}/companies")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationCompanyDto>> findAllByAndOrganizationId(
            @PathVariable Long organizationId,
            @PageableDefault Pageable pageable) {
        Page<OrganizationCompany> organizationPage = organizationCompanyService.findAllByAndOrganizationId(pageable, organizationId);
        PageDto<OrganizationCompanyDto> pageDto = PageDto.toDto(organizationPage, OrganizationCompanyService::toDto);
        return ResponseEntity.ok(pageDto);
    }

    @PostMapping(value = "/{organizationId}/companies/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrganizationCompanyResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                              @PathVariable Long organizationId,
                                                              @PathVariable Long companyId,
                                                              @Valid @RequestBody OrganizationCompanyRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organizationCompanyService.save(authenticatedUserId, organizationId, companyId, request));
    }

    @PutMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody OrganizationCompanyRequest request) throws OrganizationCompanyNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationCompanyService.replace(authenticatedUserId, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/companies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationCompanyService.delete(authenticatedUserId, id);
        return ResponseEntity.ok().build();
    }
}
