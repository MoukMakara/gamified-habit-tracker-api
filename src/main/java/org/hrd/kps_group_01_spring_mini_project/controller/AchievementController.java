package org.hrd.kps_group_01_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Achievement;
import org.hrd.kps_group_01_spring_mini_project.model.entity.AppUser;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.hrd.kps_group_01_spring_mini_project.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/achievements")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    @Operation(
            summary = "Get all achievements",
            description = "Fetches a paginated list of all achievements."
    )
    public ResponseEntity<ApiResponse<List<Achievement>>> getAllAchievements(@RequestParam(defaultValue = "1") @Positive(message = "page size can't be negative!") Integer page,
                                                                             @RequestParam(defaultValue = "10") @Positive(message = "size can't be negative!") Integer size) {
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements retrieved successfully!")
                .status(HttpStatus.OK)
                .payload(achievementService.retrievedAllAchievement(page,size))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("app-users")
    @Operation(
            summary = "Get achievements by App User ID",
            description = "Fetches a paginated list of achievements filtered by a specific App User ID."
    )
    public ResponseEntity<ApiResponse<List<Achievement>>> getAchievementByAppUserId(@RequestParam(defaultValue = "1") @Positive(message = "page size can't be negative!") Integer page,
                                                                              @RequestParam(defaultValue = "10")@Positive(message = "size can't be negative!") Integer size) {

        List<Achievement> achievements = achievementService.retrievedAchievementByAppUserId(page,size);

        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .message("Achievements for the specified App User retrieved successfully!")
                .status(HttpStatus.OK)
                .payload(achievements)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
