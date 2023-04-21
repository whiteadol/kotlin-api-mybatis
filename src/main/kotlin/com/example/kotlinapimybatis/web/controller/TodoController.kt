package com.example.kotlinapimybatis.web.controller

import com.example.kotlinapimybatis.domain.TodoRequest
import com.example.kotlinapimybatis.domain.TodoResponse
import com.example.kotlinapimybatis.service.TodoService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(
    val todoService: TodoService
) {
    private val log = LoggerFactory.getLogger(TodoController::class.java)

    @PostMapping(
        value = ["/api/todos"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTodos(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody todoRequest: TodoRequest
    ): MutableList<TodoResponse> {
        log.info("todoRequest:[{}]", todoRequest.toString())
        return todoService.getTodos(todoRequest)
    }

    @GetMapping(
        value = ["/api/todo/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTodoById(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long
    ): TodoResponse {
        log.info("id:[{}]", id)
        return todoService.getTodoById(id)
    }

    @PostMapping(
        value = ["/api/todo"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun insertTodo(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody todoRequest: TodoRequest
    ): TodoResponse {
        log.info("todoRequest:[{}]", todoRequest.toString())
        return todoService.insertTodo(todoRequest)
    }

    @PutMapping(
        value = ["/api/todo"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateTodo(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody todoRequest: TodoRequest
    ): TodoResponse {
        log.info("todoRequest:[{}]", todoRequest.toString())
        return todoService.updateTodo(todoRequest)
    }

    @DeleteMapping(
        value = ["/api/todo/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteTodo(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long
    ): TodoResponse {
        log.info("id:[{}]", id)
        return todoService.deleteTodoById(id)
    }
}