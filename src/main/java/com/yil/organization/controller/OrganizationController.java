package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.ApiError;
import com.yil.organization.base.Mapper;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.CreateOrganizationResponse;
import com.yil.organization.dto.OrganizationDto;
import com.yil.organization.dto.OrganizationRequest;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationTypeNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.Organization;
import com.yil.organization.service.OrganizationService;
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

@RestController
@RequestMapping(value = "/api/org/v1")
@RequiredArgsConstructor
@Tag(name = "Organizasyon", description = "Organizasyon işlemleri")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final Mapper<Organization, OrganizationDto> mapper = new Mapper<>(OrganizationService::toDto);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(mapper.map(organizationService.findAll(pageable)));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrganizationDto> findById(@PathVariable Long id) throws OrganizationNotFoundException {
        return ResponseEntity.ok(mapper.map(organizationService.findById(id)));
    }

    @Operation(summary = "Organizasyon eklemek için kullanılır.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Organizasyon eklendi."),
            @ApiResponse(responseCode = "403",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateOrganizationResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                             @Valid @RequestBody OrganizationRequest request) throws OrganizationNotFoundException, OrganizationTypeNotFoundException, YouAreNotOrganizationManager {
        CreateOrganizationResponse response = organizationService.save(authenticatedUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody OrganizationRequest request) throws OrganizationTypeNotFoundException, OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationService.replace(authenticatedUserId, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationService.deleteById(id, authenticatedUserId);
        return ResponseEntity.ok("Organization deleted.");
    }
}
