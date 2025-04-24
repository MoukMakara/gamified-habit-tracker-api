package org.hrd.kps_group_01_spring_mini_project.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.model.entity.HabitLog;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitLogRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.ApiResponse;
import org.hrd.kps_group_01_spring_mini_project.service.impl.HabitLogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/habit-log")
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {
    private final HabitLogServiceImpl habitLogService;

    @PostMapping
    @Operation(summary = "Create new habit log",description = "Creates a new log for a specific habit with the provided details.")
    ResponseEntity<ApiResponse<HabitLog>> createHabitLog(@RequestBody @Valid HabitLogRequest habitLogRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<HabitLog>builder()
                        .success(true)
                        .message("Habit log created successfully!")
                        .status(HttpStatus.CREATED)
                        .payload(habitLogService.createHabitLog(habitLogRequest))
                        .build()
        );
    }

    @GetMapping("/{habitUUID}")
    @Operation(summary = "Get all habit logs new habit log",description ="Fetches a paginated list of all habit logs for a specific habit by its ID." )

    ApiResponse<List<HabitLog>> getHabitLogById(@PathVariable("habitUUID") UUID habitUUID,@RequestParam(defaultValue = "10") @Min(value = 1, message = "size must be greater than 0") int size, @RequestParam(defaultValue = "1") @Positive @Min(value = 1, message = "page must be greater than 0") int page) {
        List<HabitLog> habitLogsByID = habitLogService.getAllHabitLogById(habitUUID,page,size);
         return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<List<HabitLog>>builder()
                        .success(true)
                        .message("Get all habit logs successfully!")
                        .status(HttpStatus.OK)
                        .payload(habitLogsByID)
                        .build()
        ).getBody();
    }


}