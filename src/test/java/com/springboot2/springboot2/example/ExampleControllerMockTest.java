package com.springboot2.springboot2.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExampleModelRepository exampleModelRepository;

    @BeforeEach
    void setup() {
        exampleModelRepository.deleteAll();
        ExampleModel testModel = ExampleModel.builder().name("name").email("test@gmail.com").phone("0931239809").build();
        exampleModelRepository.save(testModel);
    }

    @Test
    void getAllData_ShouldReturnListOfExampleModels() throws Exception {
        when(exampleModelRepository.findAll())
                .thenReturn(
                        List.of(
                                new ExampleModel(1L, "name", "test@gmail.com", "0931239809")
                        )
                );
        mockMvc.perform(get("/example"))
//                .andExpect(status().isOk())
                .andExpect(status().is(224))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"))
                .andExpect(jsonPath("$[0].phone").value("0931239809"));
    }

    @Test
    void createData_ShouldCreateNewExampleModel() throws Exception {
        String req = """
                {
                    "name":"test",
                    "email":"new@email.com",
                    "phone":"0987654321"
                }
                """;

        ExampleModel savedModel = new ExampleModel(1L, "test", "new@email.com", "0987654321");

        when(exampleModelRepository.save(any(ExampleModel.class)))
                .thenReturn(savedModel);

        mockMvc.perform(post("/example")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(status().is(240))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("new@email.com"))
                .andExpect(jsonPath("$.phone").value("0987654321"));
    }

}