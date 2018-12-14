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
import com.api.qerp.model.Activity;
import com.api.qerp.model.State;
import com.api.qerp.repository.ActivityRepository;

@RestController
public class ActivityController {

	@Autowired
	private ActivityRepository activityRepository;

	@PostMapping("/activity/new")
	public ResponseEntity<?> registerActivity(@Valid @RequestBody Activity activity) {
		if (activityRepository.existByName(activity.getName(), activity.getCourse().getId())) {
			throw new BadRequestException("Activity already in use.");
		}

		Activity newActivity = new Activity();
		newActivity.setName(activity.getName());
		newActivity.setDescription(activity.getDescription());
		newActivity.setReleaseDate(new Date());
		newActivity.setState(State.inicial);
		newActivity.setType(activity.getType());
		newActivity.setPdfSerialized(activity.getPdfSerialized());
		newActivity.setVideoLink(activity.getVideoLink());
		newActivity.setCourse(activity.getCourse());

		activityRepository.save(newActivity);

		return new ResponseEntity<Object>(newActivity, HttpStatus.CREATED);

	} 

	@PutMapping("/activity/update")
	public ResponseEntity<?> updateActivity(@Valid @RequestBody Activity activity) {
		if (activityRepository.existByName(activity.getName(), activity.getCourse().getId())) {

			Activity updateActivity = new Activity();
			updateActivity.setId(activity.getId());
			updateActivity.setName(activity.getName());
			updateActivity.setDescription(activity.getDescription());
			updateActivity.setReleaseDate(new Date());
			updateActivity.setState(State.inicial);
			updateActivity.setType(activity.getType());
			updateActivity.setPdfSerialized(activity.getPdfSerialized());
			updateActivity.setVideoLink(activity.getVideoLink());
			updateActivity.setCourse(activity.getCourse());

			activityRepository.save(updateActivity);

			return new ResponseEntity<Object>(updateActivity, HttpStatus.CREATED);

		} else {
			throw new BadRequestException("Activity doesn't exist.");
		}

	}

	@DeleteMapping("/activity/delete")
	public ResponseEntity<?> deleteAcitivty(@Valid @RequestBody Activity activity) {
		if (activityRepository.existByName(activity.getName(), activity.getCourse().getId())) {

			Activity deleteActivity = activity;
			activityRepository.delete(deleteActivity);

			return new ResponseEntity<Object>(deleteActivity, HttpStatus.OK);

		} else {
			throw new BadRequestException("Activity doesn't exist.");
		}

	}

	@GetMapping("/activity/list/{id}")
	public Activity getActivityById(@PathVariable(value = "id") Long id) {
		return activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", id));
	}

	@GetMapping("/activity/list/all")
	public ResponseEntity<?> getAllActivities() {
		List<Activity> activities = activityRepository.findAll();

		if (activities.isEmpty() == false) {
			return new ResponseEntity<Object>(activities, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}

	@GetMapping("/activity/get/name/{id}")
	public ResponseEntity<?> getNameById(@PathVariable(value = "id") Long id) {
		String nameActivity = activityRepository.findNameById(id);

		if (nameActivity.isEmpty() == false || nameActivity.equals(null) || nameActivity.equals("")) {
			return new ResponseEntity<Object>(nameActivity, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}
}
