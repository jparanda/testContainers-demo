package com.juan.learn.testcontainers.todo.repository;

import com.juan.learn.testcontainers.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
