package com.api.qerp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.qerp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT category.name FROM Category category  where category.id = :id")
	String findNameById(@Param("id") Long id);
	
	
	@Query("SELECT CASE WHEN COUNT(category) > 0 THEN 'true' ELSE 'false' END FROM Category category WHERE category.name= :name")
	Boolean existByName(@Param("name") String name);

	List<Category> findAll();

	Optional<Category> findById(Long id);

}
