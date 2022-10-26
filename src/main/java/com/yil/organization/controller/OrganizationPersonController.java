package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.ApiError;
import com.yil.organization.base.Mapper;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.OrganizationPersonDto;
import com.yil.organization.dto.OrganizationPersonRequest;
import com.yil.organization.dto.OrganizationPersonResponse;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationPersonNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.OrganizationPerson;
import com.yil.organization.service.OrganizationPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/org/v1")
@Tag(name = "Organizasyon Kişileri")
public class OrganizationPersonController {
    private final OrganizationPersonService organizationPersonService;
    private final Mapper<OrganizationPerson, OrganizationPersonDto> mapper = new Mapper<>(OrganizationPersonService::toDto);

    @Operation(summary = "Kişinin organizasyonlarını getirir.")
    @GetMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationPersonDto>> findAllByPersonId(
            @PathVariable Long personId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(mapper.map(organizationPersonService.findAllByPersonId(pageable, personId)));
    }

    @Operation(summary = "Organizasyondaki kişileri getirir.")
    @GetMapping("/{organizationId}/persons")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationPersonDto>> findAllByOrganizationId(
            @PathVariable Long organizationId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(mapper.map(organizationPersonService.findAllByOrganizationId(pageable, organizationId)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Organizasyona kişi eklendi."),
            @ApiResponse(responseCode = "400",
                    description = "Organizasyon bulunamadı!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "406",
                    description = "Organizasyon yöneticisi değilsiniz!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping(value = "/{organizationId}/persons/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrganizationPersonResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                             @PathVariable Long organizationId,
                                                             @PathVariable Long personId,
                                                             @Valid @RequestBody OrganizationPersonRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationPersonService.save(authenticatedUserId, organizationId, personId, request));
    }

    @PutMapping("/persons/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replaceOrganization(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                              @PathVariable Long id,
                                              @Valid @RequestBody OrganizationPersonRequest request) throws OrganizationPersonNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationPersonService.replace(authenticatedUserId, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deletePerson(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                       @PathVariable Long id) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationPersonService.deleteById(authenticatedUserId, id);
        return ResponseEntity.ok().build();
    }
}
