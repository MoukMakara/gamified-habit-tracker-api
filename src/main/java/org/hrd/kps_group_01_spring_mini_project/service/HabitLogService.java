package org.hrd.kps_group_01_spring_mini_project.service;

import org.apache.ibatis.annotations.Param;
import org.hrd.kps_group_01_spring_mini_project.model.entity.HabitLog;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitLogRequest;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    HabitLog createHabitLog( HabitLogRequest habitLogRequest);
    List<HabitLog> getAllHabitLogById(UUID Id, Integer page, Integer size);

}
