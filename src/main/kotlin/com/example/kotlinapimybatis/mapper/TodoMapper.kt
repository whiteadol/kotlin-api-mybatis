package com.example.kotlinapimybatis.mapper

import com.example.kotlinapimybatis.domain.TodoRequest
import com.example.kotlinapimybatis.domain.TodoResponse
import org.springframework.stereotype.Repository

@Repository
interface TodoMapper {

    fun getTodos(todoRequest: TodoRequest): MutableList<TodoResponse>

    fun getTodoById(id: Long): TodoResponse

    fun insertTodo(todoRequest: TodoRequest): Int

    fun updateTodo(todoRequest: TodoRequest): Int

    fun deleteTodoById(id: Long): Int
}