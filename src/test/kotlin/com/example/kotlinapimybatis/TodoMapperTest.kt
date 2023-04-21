package com.example.kotlinapimybatis

import com.example.kotlinapimybatis.domain.TodoRequest
import com.example.kotlinapimybatis.mapper.TodoMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
class TodoMapperTest {

    @Autowired
    lateinit var todoMapper: TodoMapper

    @Transactional
    @DisplayName("getTodos_To-Do 목록 조회")
    @Test
    fun testGetTodos() {

        // given
        insertTodo("Title Junit Test Insert 01", "Description Junit Test Insert 01", false)
        insertTodo("Title Junit Test Insert 02", "Description Junit Test Insert 02", true)
        insertTodo("Title Junit Test Insert 03", "Description Junit Test Insert 03", false)
        insertTodo("Title Junit Test Insert 04", "Description Junit Test Insert 04", true)
        insertTodo("Title Junit Test Insert 05", "Description Junit Test Insert 05", false)

        val todoRequest = TodoRequest().apply {
            this.title = "Title Junit Test Insert"
            this.description = "Description Junit Test Insert"
            this.completed = true
        }

        // when
        val todos = todoMapper.getTodos(todoRequest)

        // then
        Assertions.assertTrue(todos.isNotEmpty())
    }

    @Transactional
    @DisplayName("getTodoById_To-Do 상세 조회")
    @Test
    fun testGetTodoById() {

        // given
        val insertId = insertTodo("Title Junit Test Insert 01", "Description Junit Test Insert 01", false)

        // when
        val todoResponse = todoMapper.getTodoById(insertId)

        // then
        Assertions.assertEquals(insertId, todoResponse.id)
    }

    @Transactional
    @DisplayName("insertTodo_To-Do 저장")
    @Test
    fun testInsertTodo() {

        // given
        val todoRequest = TodoRequest().apply {
            this.title = "Title Junit Test Insert"
            this.description = "Description Junit Test Insert"
            this.completed = false
        }

        // when
        todoMapper.insertTodo(todoRequest)

        // then
        todoRequest.id?.let {
            val todoResponse = todoMapper.getTodoById(it)
            Assertions.assertEquals(todoRequest.title, todoResponse.title)
            Assertions.assertEquals(todoRequest.description, todoResponse.description)
            Assertions.assertEquals(todoRequest.completed, todoResponse.completed)
        } ?: throw Exception()
    }

    @Transactional
    @DisplayName("updateTodo_To-Do 수정")
    @Test
    fun testUpdateTodo() {

        // given
        val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

        val todoRequest = TodoRequest().apply {
            this.id = insertId
            this.title = "Title Junit Test Update"
            this.description = "Description Junit Test Update"
            this.completed = true
        }

        // when
        todoMapper.updateTodo(todoRequest)

        // then
        todoRequest.id?.let {
            val todoResponse = todoMapper.getTodoById(it)
            Assertions.assertEquals(todoRequest.title, todoResponse.title)
            Assertions.assertEquals(todoRequest.description, todoResponse.description)
            Assertions.assertEquals(todoRequest.completed, todoResponse.completed)
        } ?: throw Exception()
    }

    @Transactional
    @DisplayName("deleteTodoById_To-Do 삭제")
    @Test
    fun testDeleteToDoById() {

        // given
        val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

        // when
        todoMapper.deleteTodoById(insertId)

        // then
        val todoResponse = todoMapper.getTodoById(insertId)
        Assertions.assertNull(todoResponse)
    }

    fun insertTodo(
        title: String,
        description: String,
        completed: Boolean
    ): Long {

        val todoRequest = TodoRequest().apply {
            this.title = title
            this.description = description
            this.completed = completed
        }

        todoMapper.insertTodo(todoRequest)

        return todoRequest.id ?: 0
    }
}