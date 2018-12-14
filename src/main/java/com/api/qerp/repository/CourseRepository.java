package com.api.qerp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.qerp.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("SELECT course.name FROM Course course  where course.id = :id")
	String findNameById(@Param("id") Long id);

	@Query("SELECT CASE WHEN COUNT(course) > 0 THEN 'true' ELSE 'false' END FROM Course course WHERE course.name= :name")
	Boolean existByName(@Param("name") String name);
	
	@Query("SELECT course FROM Course course where course.id = :id AND course.name = :name")
	Course findById(@Param("id") Long id, @Param ("name") String name);
	
	List<Course> findAll();

	Optional<Course> findById(Long id);

}
