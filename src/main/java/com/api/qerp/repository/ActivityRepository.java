package com.api.qerp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.qerp.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("SELECT activity.name FROM Activity activity  where activity.id = :id")
	String findNameById(@Param("id") Long id);
	
	@Query("SELECT CASE WHEN COUNT(activity) > 0 THEN 'true' ELSE 'false' END FROM Activity activity WHERE activity.name= :name AND activity.course.id = :id ")
	Boolean existByName(@Param("name") String name, @Param("id") Long id);

	List<Activity> findAll();

	Optional<Activity> findById(Long id);

}
