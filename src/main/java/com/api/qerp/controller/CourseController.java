package com.api.qerp.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.qerp.exception.BadRequestException;
import com.api.qerp.exception.ResourceNotFoundException;
import com.api.qerp.model.Course;
import com.api.qerp.model.State;
import com.api.qerp.repository.CourseRepository;
import com.api.qerp.repository.UserRepository;

@RestController
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@SuppressWarnings("unused")
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/course/new")
	public ResponseEntity<?> registerCourse(@Valid @RequestBody Course course) {
		if (courseRepository.existByName(course.getName())) {
			throw new BadRequestException("Course already in use.");
		}

		Course newCourse = new Course();
		newCourse.setName(course.getName());
		newCourse.setDescription(course.getDescription());
		newCourse.setHours(course.getHours());
		newCourse.setPrice(course.getPrice());
		newCourse.setDificulty(course.getDificulty());
		newCourse.setCategory(course.getCategory());
		newCourse.setReleaseDate(new Date());
		newCourse.setState(State.inicial);
		newCourse.setUsers(course.getUsers());

		courseRepository.save(newCourse);

		return new ResponseEntity<Object>(newCourse, HttpStatus.CREATED);

	}

	@PutMapping("/course/update")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course) {
		if (courseRepository.existByName(course.getName())) {

			Course updateCourse = new Course();
			updateCourse.setId(course.getId());
			updateCourse.setName(course.getName());
			updateCourse.setDescription(course.getDescription());
			updateCourse.setHours(course.getHours());
			updateCourse.setPrice(course.getPrice());
			updateCourse.setDificulty(course.getDificulty());
			updateCourse.setCategory(course.getCategory());
			updateCourse.setReleaseDate(new Date());
			updateCourse.setState(State.modified);
			

			courseRepository.save(updateCourse);

			return new ResponseEntity<Object>(updateCourse, HttpStatus.CREATED);

		} else {
			throw new BadRequestException("Course doesn't exist.");
		}

	}

	@DeleteMapping("/course/delete")
	public ResponseEntity<?> deleteCourse(@Valid @RequestBody Course course) {
		if (courseRepository.existByName(course.getName())) {

			Course deleteCourse = course;
			courseRepository.delete(deleteCourse);

			return new ResponseEntity<Object>(deleteCourse, HttpStatus.OK);

		} else {
			throw new BadRequestException("Course doesn't exist.");
		}

	}

	@GetMapping("/course/list/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable(value = "id") Long id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
		return new ResponseEntity<Object>(course, HttpStatus.OK);

	}

	@GetMapping("/course/list/all")
	public ResponseEntity<?> getAllUsers() {
		List<Course> courses = courseRepository.findAll();

		if (courses.isEmpty() == false) {
			return new ResponseEntity<Object>(courses, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/course/get/name/{id}")
	public ResponseEntity<?> getNameById(@PathVariable(value = "id") Long id) {
		String courseName = courseRepository.findNameById(id);
		if (courseName.isEmpty() || courseName.equals("") || courseName.equals(null)) {
			return new ResponseEntity<Object>(courseName, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}
}
