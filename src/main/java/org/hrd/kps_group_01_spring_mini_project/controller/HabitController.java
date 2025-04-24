package org.hrd.kps_group_01_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.helper.AuthUtils;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Habit;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitRequest;

import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;
import org.springframework.http.ResponseEntity;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.hrd.kps_group_01_spring_mini_project.service.HabitService;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class HabitController {
    private final HabitService habitService;
    @GetMapping
    @Operation(summary = "Get all habits", description = "Fetches a paginated list of all habits.")
    public ResponseEntity<ApiResponse<List<HabitDto>>> getAllHabits(@RequestParam(defaultValue = "1")  @Positive(message = "must be greater than 0") Integer page,
                                                                    @RequestParam(defaultValue = "10") @Positive(message = "must be greater than 0") Integer size){

        ApiResponse<List<HabitDto>> response = ApiResponse.<List<HabitDto>>builder()
                .success(true)
                .message("Fetched all habits successfully!")
                .payload(habitService.getAllHabits(page, size))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new habit", description = "Creates a new habit with the provided details.")
    public ResponseEntity<ApiResponse<HabitDto>> createHabit(@RequestBody @Valid HabitRequest habitRequest){
        HabitDto habitDto = habitService.createHabit(habitRequest);

        ApiResponse<HabitDto> response = ApiResponse.<HabitDto>builder()
                .success(true)
                .message("Create new habit is successfully")
                .status(HttpStatus.CREATED)
                .payload(habitDto)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // getHabitById
    @GetMapping("/{habit-id}")
    @Operation(summary = "Get habit by ID",description = "Fetches a specific habit by its ID.")
    public ResponseEntity<ApiResponse<HabitDto>> getHabitById(@PathVariable("habit-id") @NotNull(message = "Habit ID cannot be null") UUID habitUUID){
        HabitDto habitDto = habitService.getHabitById(habitUUID);

        ApiResponse<HabitDto> response = ApiResponse.<HabitDto>builder()
                .success(true)
                .message("Habit fetched successfully!")
                .status(HttpStatus.OK)
                .payload(habitDto)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    // deleteHabitById
    @DeleteMapping("/{habit-id}")
    @Operation(summary = "Delete habit by ID",description = "Deletes a habit by its ID.")
    public ResponseEntity<ApiResponse<Void>> deleteHabitById(@PathVariable("habit-id") @NotNull(message = "Habit ID cannot be null") UUID habitUUID){

        habitService.deleteHabitById(habitUUID);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Habit deleted successfully!")
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    // updateHabitById
    @PutMapping("/{habit-id}")
    @Operation(summary = "Update habit by ID", description = "Updates the details of an existing habit by its ID.")
    public ResponseEntity<ApiResponse<HabitDto>> updateHabitById(@PathVariable("habit-id") @NotNull(message = "Habit ID cannot be null") UUID habitUUID, @RequestBody @Valid HabitRequest habitRequest){

        HabitDto habitDto = habitService.updateHabitById(habitUUID, habitRequest);

        ApiResponse<HabitDto> response = ApiResponse.<HabitDto>builder()
                .success(true)
                .message("Habit updated successfully!")
                .status(HttpStatus.OK)
                .payload(habitDto)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
