package org.hrd.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.kps_group_01_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.hrd.kps_group_01_spring_mini_project.model.entity.Habit;
import org.hrd.kps_group_01_spring_mini_project.model.entity.HabitLog;
import org.hrd.kps_group_01_spring_mini_project.model.request.HabitLogRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Select("""
select * from habit_logs
where habit_id = #{habitUUID}
 offset #{size}*(#{page}-1)
limit #{size};
""")
    @Results(id = "habitLogsMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id"),
            @Result(property = "logDate", column = "log_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "xpEarned", column = "xp_earned"),
            @Result(property = "habit", column = "habit_id",
                    one = @One(select = "org.hrd.kps_group_01_spring_mini_project.repository.HabitRepository.getHabitByIdOnly"))
    })
    List<HabitLog> findHabitLogsById(@Param("habitUUID")UUID id, Integer page, Integer size);

    @Select("""
        INSERT INTO habit_logs(status, habit_id, xp_earned, log_date)
        VALUES (#{habitrq.status}, #{habitrq.habitId}, #{xpEarned}, NOW())
        RETURNING *
        """)
    @ResultMap("habitLogsMapper")
    HabitLog createHabitLog(@Param("habitrq") HabitLogRequest habitLog, Integer xpEarned);

}
