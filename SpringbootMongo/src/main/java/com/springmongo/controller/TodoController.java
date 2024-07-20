package com.springmongo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmongo.model.Todo;
import com.springmongo.repo.TodoRepo;

import jakarta.validation.Valid;

@RestController
public class TodoController {

	@Autowired
	private TodoRepo todoRepo;

	@GetMapping("/todos")
	public ResponseEntity<?> getALLTodos() {
		List<Todo> list = todoRepo.findAll();
		if (list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No todos found");
		}
	}

	@PostMapping("/saveTodo")
	public ResponseEntity<Todo> createNewTodo(@Valid@RequestBody Todo todo) {
		try {
			todo.setCreatedAt(LocalDateTime.now());
			Todo savedTodo = todoRepo.save(todo);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(todo);
		}
	}

	@GetMapping("/getTodo/{id}")
	public ResponseEntity<Todo> getTodo(@PathVariable("id") String id) {
		Optional<Todo> todo = todoRepo.findById(id);
		if (todo.isPresent()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(todo.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/getTodo/{id}")
	public ResponseEntity<Todo> updateById(@PathVariable("id") String id, @Valid@RequestBody Todo todo) {
		Optional<Todo> td_db = todoRepo.findById(id);
		if (td_db.isPresent()) {
			Todo todosave = td_db.get();
			todosave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todosave.getCompleted());
			todosave.setDescription(todo.getDescription() != null ? todo.getDescription() : todosave.getDescription());
			todo.setUpdatedAt(LocalDateTime.now());
			todosave.setTodo(todo.getTodo() != null ? todo.getTodo() : todosave.getTodo());
			todoRepo.save(todosave);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(todosave);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/deletetodo/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable("id") String id) {
		Optional<Todo> todo = todoRepo.findById(id);
		if (todo.isPresent()) {
			todoRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted Successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo Not Found");
		}
	}
	
	@GetMapping("/getByDescription")
	public ResponseEntity<List<Todo>> getByDescription(@RequestParam String description)
	{ 
		List<Todo> list=todoRepo.findTodoByDescription(description);
		
		if(list!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
