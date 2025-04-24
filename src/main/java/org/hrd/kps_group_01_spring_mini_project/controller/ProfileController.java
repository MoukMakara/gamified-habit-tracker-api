package org.hrd.kps_group_01_spring_mini_project.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.model.request.ProfileRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;
import org.hrd.kps_group_01_spring_mini_project.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Get user profile",description = "Fetches the details of the currently authenticated user.")
    @GetMapping
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserProfile() {
        AppUserResponse user = profileService.getUserProfile();

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("Success")
                .status(HttpStatus.OK)
                .payload(user)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update user profile",description = "Updates the details of the currently authenticated user. Provide the necessary fields in the request body.")
    @PutMapping
    public ResponseEntity<ApiResponse<AppUserResponse>> updateUserProfile(@RequestBody ProfileRequest profileRequest) {
        AppUserResponse user = profileService.updateUserProfile(profileRequest);

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("Success")
                .status(HttpStatus.OK)
                .payload(user)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete user profile",description = "Deletes the currently authenticated user's profile. This action cannot be undone.")
    @DeleteMapping
    public ResponseEntity<ApiResponse<AppUserResponse>> deleteUserProfile() {
         profileService.deleteUserProfile();

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("Success")
                .status(HttpStatus.OK)
                .payload(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
