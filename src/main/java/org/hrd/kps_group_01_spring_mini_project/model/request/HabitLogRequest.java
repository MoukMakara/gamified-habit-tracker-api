package org.hrd.kps_group_01_spring_mini_project.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLogRequest {
        @Schema(defaultValue = "COMPLETED")
        @NotBlank(message = "Status must be not empty!")
        private String status;
        @NotNull(message = "Habit Id must be not null!")
        private UUID habitId;
}
