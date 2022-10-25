package com.yil.organization.controller;

import com.yil.organization.base.ApiConstant;
import com.yil.organization.base.Mapper;
import com.yil.organization.base.PageDto;
import com.yil.organization.dto.OrganizationUserDto;
import com.yil.organization.dto.OrganizationUserRequest;
import com.yil.organization.exception.OrganizationNotFoundException;
import com.yil.organization.exception.OrganizationPersonNotFoundException;
import com.yil.organization.exception.OrganizationUserNotFoundException;
import com.yil.organization.exception.YouAreNotOrganizationManager;
import com.yil.organization.model.OrganizationUser;
import com.yil.organization.service.OrganizationUserService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Organizasyon Kullanıcıları")
public class OrganizationUserController {
    private final OrganizationUserService organizationUserService;
    private final Mapper<OrganizationUser, OrganizationUserDto> mapper = new Mapper<>(OrganizationUserService::toDto);

    @Operation(summary = "Organizasyondaki kullanıcıları getirir.")
    @GetMapping("/{organizationId}/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationUserDto>> findAllByOrganizationId(
            @PathVariable Long organizationId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(mapper.map(organizationUserService.findAllByOrganizationId(pageable, organizationId)));
    }

    @Operation(summary = "Kullanıcının organizasyonlarını getirir.")
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<OrganizationUserDto>> findAllByUserId(
            @PathVariable Long userId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(mapper.map(organizationUserService.findAllByUserId(pageable, userId)));
    }

    @PostMapping(value = "/{organizationId}/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                 @PathVariable Long organizationId,
                                 @PathVariable Long userId,
                                 @Valid @RequestBody OrganizationUserRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationUserService.save(authenticatedUserId, organizationId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{organizationId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long organizationId,
                                  @PathVariable Long userId,
                                  @Valid @RequestBody OrganizationUserRequest request) throws OrganizationNotFoundException, YouAreNotOrganizationManager, OrganizationUserNotFoundException {
        organizationUserService.replace(authenticatedUserId, organizationId, userId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{organizationId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                 @PathVariable Long organizationId,
                                 @PathVariable Long userId) throws OrganizationNotFoundException, YouAreNotOrganizationManager {
        organizationUserService.delete(authenticatedUserId, organizationId, userId);
        return ResponseEntity.ok().build();
    }
}
