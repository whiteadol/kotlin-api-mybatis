package com.example.kotlinapimybatis

import com.example.kotlinapimybatis.domain.TodoRequest
import com.example.kotlinapimybatis.mapper.TodoMapper
import com.example.kotlinapimybatis.service.TodoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
class TodoServiceTest {

    @Autowired
    lateinit var todoService: TodoService

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
        val todoResponse = todoService.getTodos(todoRequest)

        // then
        assertTrue(todoResponse.isNotEmpty())
    }

    @Transactional
    @DisplayName("getTodoById_To-Do 상세 조회")
    @Test
    fun testGetTodoById() {

        // given
        val title = "Title Junit Test Insert"
        val description = "Description Junit Test Insert"
        val completed = false
        val insertId = insertTodo(title, description, completed)

        // when
        val todoResponse = todoService.getTodoById(insertId)

        // then
        assertEquals(title, todoResponse.title)
        assertEquals(description, todoResponse.description)
        assertEquals(completed, todoResponse.completed)
    }

    @Transactional
    @DisplayName("insertTodo_To-Do 저장")
    @Test
    fun testInsertTodo() {

        // given
        val todoRequest = TodoRequest().apply {
            this.title = "Title Junit Test Insert"
            this.description = "Description Junit Test Insert"
            this.completed = true
        }

        // when
        val todoResponse = todoService.insertTodo(todoRequest)

        // then
        assertEquals(todoRequest.title, todoResponse.title)
        assertEquals(todoRequest.description, todoResponse.description)
        assertEquals(todoRequest.completed, todoResponse.completed)
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
        val todoResponse = todoService.updateTodo(todoRequest)

        // then
        assertEquals(todoRequest.title, todoResponse.title)
        assertEquals(todoRequest.description, todoResponse.description)
        assertEquals(todoRequest.completed, todoResponse.completed)
    }

    @Transactional
    @DisplayName("deleteTodoById_To-Do 삭제")
    @Test
    fun testDeleteTodoById() {

        // given
        val insertId = insertTodo("Title Junit Test Insert", "Description Junit Test Insert", false)

        // when
        val todoResponse = todoService.deleteTodoById(insertId)

        // then
        assertNull(todoResponse)
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

    @DisplayName("insertTodosFailed_To-Do 저장 시 title 이 # 으로 시작하는 경우 RuntimeException 발생")
    @Test
    fun testInsertTodosFailed() {

        // given
        val todoRequests = mutableListOf<TodoRequest>()

        todoRequests.add(
            TodoRequest().apply {
                this.title = "Title Junit Test Insert 01"
                this.description = "Description Junit Test Insert 01"
                this.completed = false
            }
        )

        todoRequests.add(
            TodoRequest().apply {
                this.title = "Title Junit Test Insert 02"
                this.description = "Description Junit Test Insert 02"
                this.completed = false
            }
        )

        todoRequests.add(
            TodoRequest().apply {
                this.title = "#Title Junit Test Insert 03"
                this.description = "Description Junit Test Insert 03"
                this.completed = false
            }
        )

        // when
        var result = 0
        try {
            result = todoService.insertTodosFailed(todoRequests)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // then
        assertEquals(0, result)
    }
}