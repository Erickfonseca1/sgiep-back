package com.sgiep.sgiep_back.repository;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findAll(Pageable pageable);

    @Query("SELECT s FROM Activity a JOIN a.students s WHERE a.id = :activityId")
    List<User> findCitizensByActivity(@Param("activityId") Long activityId);

    @Query("SELECT a FROM Activity a JOIN a.schedules s WHERE s.startTime >= :startTime AND s.endTime <= :endTime")
    List<Activity> findActivitiesByTimeRange(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

    @Query("SELECT a FROM Activity a WHERE a.professor.id = :professorId")
    List<Activity> findByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT a FROM Activity a WHERE a.professor.id = :professorId " +
       "AND (:name IS NULL OR a.name LIKE %:name%) " +
       "AND (:location IS NULL OR a.location LIKE %:location%)")
    List<Activity> findByProfessorIdAndFilters(@Param("professorId") Long professorId, 
                                           @Param("name") String name, 
                                           @Param("location") String location);

    @Query("SELECT a FROM Activity a JOIN a.students s WHERE s.id = :citizenId")
    List<Activity> findActivitiesByCitizenId(@Param("citizenId") Long citizenId);

    @Query("SELECT a FROM Activity a WHERE a.professor.id = :professorId")
    List<Activity> findActivitiesByProfessorId(@Param("professorId") Long professorId);
}
