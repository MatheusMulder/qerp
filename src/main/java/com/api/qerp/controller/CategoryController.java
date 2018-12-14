package com.api.qerp.controller;

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
import com.api.qerp.model.Category;
import com.api.qerp.repository.CategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/category/new")
	public ResponseEntity<?> registerCategory(@Valid @RequestBody Category category) {
		if (categoryRepository.existByName(category.getName())) {
			throw new BadRequestException("Category already in use.");
		}

		Category newCategory = new Category();
		newCategory.setName(category.getName());

		categoryRepository.save(newCategory);

		return new ResponseEntity<Object>(newCategory, HttpStatus.CREATED);

	}

	@PutMapping("/category/update")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category) {
		if (categoryRepository.existByName(category.getName())) {

			Category updateCategory = new Category();
			updateCategory.setId(category.getId());
			updateCategory.setName(category.getName());

			categoryRepository.save(updateCategory);

			return new ResponseEntity<Object>(updateCategory, HttpStatus.CREATED);

		} else {
			throw new BadRequestException("Category doesn't exist.");
		}

	}

	@DeleteMapping("/category/delete")
	public ResponseEntity<?> deleteCategory(@Valid @RequestBody Category category) {
		if (categoryRepository.existByName(category.getName())) {

			Category deleteCategory = category;
			categoryRepository.delete(deleteCategory);

			return new ResponseEntity<Object>(deleteCategory, HttpStatus.OK);

		} else {
			throw new BadRequestException("Category doesn't exist.");
		}

	}

	@GetMapping("/category/list/{id}")
	public Category getCategoryById(@PathVariable(value = "id") Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
	}

	@GetMapping("/category/list/all")
	public ResponseEntity<?> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty() == false) {
			return new ResponseEntity<Object>(categories, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}

	@GetMapping("/category/get/name/{id}")
	public ResponseEntity<?> getNameById(@PathVariable(value = "id") Long id) {
		String category = categoryRepository.findNameById(id);
		if (category.isEmpty() == false) {
			return new ResponseEntity<Object>(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		}
	}
}
