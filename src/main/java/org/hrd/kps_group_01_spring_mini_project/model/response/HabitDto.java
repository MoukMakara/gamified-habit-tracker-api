package org.hrd.kps_group_01_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.helper.Frequency;
import org.hrd.kps_group_01_spring_mini_project.model.entity.AppUser;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDto {
    private UUID habitId;
    private String title;
    private String description;
    private Frequency frequency;
    private Boolean isActive;
    private AppUserResponse appUserResponse;
    private LocalDateTime createdAt;
}
