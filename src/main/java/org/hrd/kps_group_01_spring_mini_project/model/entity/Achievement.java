package org.hrd.kps_group_01_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private UUID achievementId;
    private String title;
    private String description;
    private String badge;
    private Integer xpRequired;
}
