package satoshiyamamoto.tastewaf.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TodoControllerTest {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getTodosOk() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("classpath:sql/fixtures.sql")
    public void getTodoOk() throws Exception {
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTodoNotFound() throws Exception {
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postTodoOk() throws Exception {
        mockMvc.perform(post("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"title\",\"completed\": false}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void postTodoUnprocessable() throws Exception {
        mockMvc.perform(post("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"\",\"completed\": false}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Sql("classpath:sql/fixtures.sql")
    public void putTodoOk() throws Exception {
        mockMvc.perform(put("/todos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"title\",\"completed\": false}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void putTodoNotFound() throws Exception {
        mockMvc.perform(put("/todos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"title\",\"completed\": false}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("classpath:sql/fixtures.sql")
    public void putTodosUnprocessable() throws Exception {
        mockMvc.perform(put("/todos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"   \",\"completed\": false}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Sql("classpath:sql/fixtures.sql")
    public void deleteTodoOk() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTodoNotFound() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNotFound());
    }
}
