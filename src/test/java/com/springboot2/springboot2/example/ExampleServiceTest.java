package com.springboot2.springboot2.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {

    @InjectMocks
    private ExampleService exampleService;
    @Mock
    private ExampleModelRepository exampleModelRepository;

    @Test
    void test_getAllData_ShouldReturnEmptyList() {
        when(exampleModelRepository.findAll())
                .thenReturn(
                        List.of()
                );
        List<ExampleModel> allData = exampleService.getAllData();
        Assertions.assertThat((allData)).isEmpty();

        verify(exampleModelRepository, times(1)).findAll();
    }

    @Test
    void test_getAllData_ShouldReturnDataList() {
        when(exampleModelRepository.findAll())
                .thenReturn(
                        List.of(new ExampleModel(), new ExampleModel())
                );
        List<ExampleModel> allData = exampleService.getAllData();
        Assertions.assertThat((allData)).hasSize(2);

    }

    @Test
    void test_createData_ShouldReturnExampleModel() {
//        ExampleRequest mockRequest = new ExampleRequest("name", "test@gmail.com", "09876767654");
//        ExampleModel mockResponse = new ExampleModel(1L, "name", "test@gmail.com", "09876767654");

        ExampleRequest mockRequest = new ExampleRequest();
        ExampleModel mockResponse = new ExampleModel();
        mockResponse.setId(1L);

        when(exampleModelRepository.save(any(ExampleModel.class))).thenReturn(mockResponse);
        ExampleModel result = exampleService.createData(mockRequest);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result).isEqualTo(mockResponse);

        verify(exampleModelRepository,times(1)).save(any(ExampleModel.class));
    }
}