package com.juan.learn.testcontainers.todo.controller;

import com.juan.learn.testcontainers.todo.model.Todo;
import com.juan.learn.testcontainers.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAll() {
        log.info("Calling getAll API method");
        List<Todo> list = todoService.findAll();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> get(@PathVariable("id") Long id) {
        log.info("Calling get API method for todo {}", id);
        Optional<Todo> todoOpt = todoService.findById(id);
        return todoOpt
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo todo) {
        log.debug("calling create API method for {}", todo);
        return new ResponseEntity<>(todoService.save(todo), new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        log.debug("calling delete API method for id: {}", id);
        return todoService.findById(id)
                .map(todo -> {
                    todoService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
