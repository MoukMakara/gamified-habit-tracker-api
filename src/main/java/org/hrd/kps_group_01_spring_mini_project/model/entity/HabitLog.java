package org.hrd.kps_group_01_spring_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLog {
    private UUID habitLogId;
    private LocalDateTime logDate;
    private String status;
    @JsonIgnore
    private UUID HabitId;
    private Integer xpEarned;
    private HabitDto habit;
}
