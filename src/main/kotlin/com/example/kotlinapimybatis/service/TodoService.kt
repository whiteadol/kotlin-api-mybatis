package com.example.kotlinapimybatis.service

import com.example.kotlinapimybatis.domain.TodoRequest
import com.example.kotlinapimybatis.domain.TodoResponse
import com.example.kotlinapimybatis.mapper.TodoMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class TodoService(
    val todoMapper: TodoMapper
) {

    fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse> {
        return todoMapper.getTodos(todoRequest)
    }

    fun getTodoById(id: Long): TodoResponse {
        return todoMapper.getTodoById(id)
    }

    fun insertTodo(todoRequest: TodoRequest): TodoResponse {
        var todoResponse = TodoResponse()
        val result = todoMapper.insertTodo(todoRequest)
        if (result > 0) {
            todoRequest.id?.let {
                todoResponse = todoMapper.getTodoById(it)
            }
        }

        return todoResponse
    }

    fun updateTodo(todoRequest: TodoRequest): TodoResponse {
        var todoResponse = TodoResponse()
        val result = todoMapper.updateTodo(todoRequest)
        if (result > 0) {
            todoRequest.id?.let {
                todoResponse = todoMapper.getTodoById(it)
            }
        }
        return todoResponse
    }

    fun deleteTodoById(id: Long): TodoResponse {
        var todoResponse = TodoResponse()
        val result = todoMapper.deleteTodoById(id)
        if (result > 0) {
            todoResponse = todoMapper.getTodoById(id)
        }
        return todoResponse
    }

    @Transactional
    fun insertTodosFailed(todoRequests: List<TodoRequest>): Int {

        var result = 0
        for (todoRequest in todoRequests) {
            if (todoRequest.title.startsWith("#")) {
                throw RuntimeException("title 이 # 으로 시작")
            }
            result += todoMapper.insertTodo(todoRequest)
        }
        return result
    }
}