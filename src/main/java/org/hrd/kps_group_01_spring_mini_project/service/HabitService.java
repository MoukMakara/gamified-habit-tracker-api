package org.hrd.kps_group_01_spring_mini_project.service;

import org.hrd.kps_group_01_spring_mini_project.model.request.HabitRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;

import java.util.List;
import java.util.UUID;

public interface HabitService {
    List<HabitDto> getAllHabits(Integer page, Integer size);
    HabitDto createHabit(HabitRequest habitRequest);
    HabitDto getHabitById(UUID id);
    void deleteHabitById(UUID id);
    HabitDto updateHabitById(UUID id, HabitRequest habitRequest);
}
