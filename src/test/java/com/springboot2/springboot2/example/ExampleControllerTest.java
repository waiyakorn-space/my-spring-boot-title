package com.springboot2.springboot2.example;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private  ExampleModelRepository exampleModelRepository ;

    @BeforeEach
    void setup(){
        exampleModelRepository.deleteAll();
        ExampleModel testModel = ExampleModel.builder().name("name").email("test@gmail.com").phone("0931239809").build();
        exampleModelRepository.save(testModel);
    }

    @Test
    void getAllData_ShouldReturnListOfExampleModels() throws Exception {
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