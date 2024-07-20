package com.springmongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springmongo.model.Todo;


@Repository
public interface TodoRepo extends MongoRepository<Todo, String>{

}
