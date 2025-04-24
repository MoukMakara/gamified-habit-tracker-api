package org.hrd.kps_group_01_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrd.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.hrd.kps_group_01_spring_mini_project.helper.AuthUtils;
import org.hrd.kps_group_01_spring_mini_project.helper.Frequency;
import org.hrd.kps_group_01_spring_mini_project.model.entity.AppUser;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Habit;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitRequest;
import org.hrd.kps_group_01_spring_mini_project.model.response.HabitDto;
import org.hrd.kps_group_01_spring_mini_project.repository.AppUserRepository;
import org.hrd.kps_group_01_spring_mini_project.repository.HabitRepository;
import org.hrd.kps_group_01_spring_mini_project.service.HabitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private static final Logger log = LoggerFactory.getLogger(HabitServiceImpl.class);
    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;


    @Override
    public List<HabitDto> getAllHabits(Integer page, Integer size) {
        UUID appUserUUID = authUtils.getCurrentUserIdentifier();

        return habitRepository.getAllHabits(page, size, appUserUUID);
    }

    @Override
    public HabitDto createHabit(HabitRequest habitRequest) {
        UUID appUserUUID = authUtils.getCurrentUserIdentifier();
        habitRequest.setFrequency(habitRequest.getFrequency().toUpperCase());
        Arrays.stream(Frequency.values())
                .filter(freq -> freq.name().equalsIgnoreCase(habitRequest.getFrequency()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid frequency available frequency is DAILY, WEEKLY, MONTHLY!"));


        return habitRepository.createHabit(habitRequest, appUserUUID);
    }

    @Override
    public HabitDto getHabitById(UUID habitUUID) {
        UUID appUserUUID = authUtils.getCurrentUserIdentifier();
        HabitDto habitDto = habitRepository.getHabitById(habitUUID, appUserUUID);

        if (habitDto == null) {
            throw new NotFoundException("Habit with ID " + habitUUID + " not found!");
        } else {
            return habitDto;
        }
    }

    @Override
    public void deleteHabitById(UUID habitUUID) {
        UUID appUserUUID = authUtils.getCurrentUserIdentifier();
        HabitDto habitDto = habitRepository.getHabitById(habitUUID, appUserUUID);

        if (habitDto == null) {
            throw new NotFoundException("Habit with ID " + habitUUID + " not found");
        }

        habitRepository.deleteHabitById(habitUUID, appUserUUID);
    }

    @Override
    public HabitDto updateHabitById(UUID habitUUID, HabitRequest habitRequest) {
        UUID appUserUUID = authUtils.getCurrentUserIdentifier();
        habitRequest.setFrequency(habitRequest.getFrequency().toUpperCase());
        Arrays.stream(Frequency.values())
                .filter(freq -> freq.name().equalsIgnoreCase(habitRequest.getFrequency()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid frequency available frequency is DAILY, WEEKLY, MONTHLY!"));

        HabitDto habitDto = habitRepository.updateHabitById(habitUUID, habitRequest, appUserUUID);

        if (habitDto == null) {
            throw new NotFoundException("Habit with ID " + habitUUID + " not found!");
        } else {
            return habitDto;
        }
    }
}