package com.springmongo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmongo.model.Todo;


@Repository
public interface TodoRepo extends MongoRepository<Todo, String>{
	
	@Query("{ 'description': ?0}")
    List<Todo> findTodoByDescription(String description);

}
