package org.hrd.kps_group_01_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.helper.Frequency;
import org.hrd.kps_group_01_spring_mini_project.model.response.AppUserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    private UUID habitId;
    private String title;
    private String description;
    private Frequency frequency;
    private Boolean isActive;
    private AppUser appUserResponse;
    private LocalDateTime createdAt;
}
