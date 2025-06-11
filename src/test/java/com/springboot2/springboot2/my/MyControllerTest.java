package com.springboot2.springboot2.my;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MyModelRepository myModelRepository;

    MyModel firstUser;

    @BeforeEach
    void setup() {
        myModelRepository.deleteAll();
        firstUser = myModelRepository.save(MyModel.builder().name("Test User").email("test@gmail.com").phone("11111").build());

    }

    @Test
    void getAllUser_ShouldReturnListOfMyModels() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"))
                .andExpect(jsonPath("$[0].phone").value("11111"));
    }

    @Test
    void getUserByEmail_ShouldReturnNoFound() throws Exception {
        String notFoundEmail = "notfound@gmail.com";

        mockMvc.perform(get("/users/byEmail")
                        .param("email", notFoundEmail))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User with email " + notFoundEmail + " not found"));

    }

    @Test
    void getUserByEmail_ShouldReturnMyModel() throws Exception {
        mockMvc.perform(get("/users/byEmail")
                        .param("email", firstUser.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.phone").value("11111"));
    }

    @Test
    void getUserById_ShouldReturnNoFound() throws Exception {
        long notFoundId = 999;

        mockMvc.perform(get("/users/byId").header("id",notFoundId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User with id " + notFoundId + " not found"));

    }
    @Test
    void getUserById_FormHeader_ShouldReturnMyModel() throws Exception {
        mockMvc.perform(get("/users/byId").header("id", firstUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.phone").value("11111"));
    }

    @Test
    void createUser_ShouldCreateNewMyModel() throws Exception {
        String mockRequest = """
                {
                    "name":"Test 2",
                    "email":"new@gmail.com",
                    "phone":"22222"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test 2"))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(jsonPath("$.phone").value("22222"));
    }

    @Test
    void updateUser_ShouldReturnUpdatedMyModel() throws Exception {
        MyModel userForUpdate = myModelRepository.save(MyModel.builder().name("Test ToUpdate").email("toupdate@gmail.com").phone("33333").build());
        String mockRequest = """
                {
                    "name":"Test Updated",
                    "email":"updated@gmail.com",
                    "phone":"55555"
                }
                """;

        mockMvc.perform(put("/users/" + userForUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userForUpdate.getId()))
                .andExpect(jsonPath("$.name").value("Test Updated"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"))
                .andExpect(jsonPath("$.phone").value("55555"));
    }

    @Test
    void deleteUser_ShouldReturnSuccess() throws Exception {
        MyModel userForDelete = myModelRepository.save(MyModel.builder().name("Test ToDelete").email("todelete@gmail.com").phone("44444").build());

        mockMvc.perform(delete("/users/" + userForDelete.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("The user has been deleted successfully."));
    }
}