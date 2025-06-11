package com.springboot2.springboot2.my;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyServiceTest {

    @InjectMocks
    private MyService myService;
    @Mock
    private MyModelRepository myModelRepository;

    // Get All Users
    @Test
    void test_getAllData_ShouldReturnEmptyList() {
        when(myModelRepository.findAll())
                .thenReturn(
                        List.of()
                );
        List<MyModel> allData = myService.getAllUsers();
        Assertions.assertThat(allData).isEmpty();
        verify(myModelRepository, times(1)).findAll();
    }

    @Test
    void test_getAllData_ShouldReturnMyModelList() {
        when(myModelRepository.findAll())
                .thenReturn(
                        List.of(new MyModel(), new MyModel())
                );
        List<MyModel> allData = myService.getAllUsers();
        Assertions.assertThat(allData).hasSize(2);
        verify(myModelRepository, times(1)).findAll();
    }

    // Get User By Email From Param Not found
    @Test
    void test_getByEmail_ShouldReturnNotFound() {
        String testEmail = "notfoundtest@gmial.com";

        when(myModelRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> myService.getUserByEmail(testEmail))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with email " + testEmail + " not found");

        verify(myModelRepository, times(1)).findByEmail(testEmail);
    }

    // Get User By Email From Param
    @Test
    void test_getByEmail_ShouldReturnMyModel() {
        String testEmail = "test@gmail.com";
        MyModel mockModel = new MyModel(1L, "Test User", "test@gmail.com", "11111");

        when(myModelRepository.findByEmail(testEmail)).thenReturn(Optional.of(mockModel));

        MyModel result = myService.getUserByEmail(testEmail);

        Assertions.assertThat(result).isEqualTo(mockModel);

        verify(myModelRepository, times(1)).findByEmail(testEmail);
    }

    // Get User By id From Header Not found
    @Test
    void test_getById_ShouldReturnNotFound() {
        long id = 1;

        when(myModelRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> myService.getUserById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with id " + id + " not found");

        verify(myModelRepository, times(1)).findById(id);
    }

    // Get User By id From Header
    @Test
    void test_getById_ShouldReturnMyModel() {
        long id = 1;
        MyModel mockModel = new MyModel(1L, "Test User", "test@gmail.com", "11111");

        when(myModelRepository.findById(id)).thenReturn(Optional.of(mockModel));

        MyModel result = myService.getUserById(id);
        Assertions.assertThat(result).isEqualTo(mockModel);

        verify(myModelRepository, times(1)).findById(id);
    }

    // Create User
    @Test
    void test_createUser_ShouldReturnMyModel() {
        MyRequest mockRequest = new MyRequest();
        MyModel mockResponse = new MyModel();
        mockResponse.setId(1L);

        when(myModelRepository.save(any(MyModel.class))).thenReturn(mockResponse);

        MyModel result = myService.createUser(mockRequest);
        Assertions.assertThat(result).isNotNull().isEqualTo(mockResponse);
        Assertions.assertThat(result.getId()).isNotNull().isEqualTo(1L);

        verify(myModelRepository, times(1)).save(any(MyModel.class));
    }

    //Update User
    @Test
    void test_updateUser_ShouldReturnMyModel() {
        long id = 1;
        MyRequest mockRequest = new MyRequest( "Test Update", "test_update@gmail.com", "99999");
        MyModel mockExistUser = new MyModel(1L, "Test User", "test@gmail.com", "11111");
        MyModel mockUpdatedUser = new MyModel(1L, "Test Update", "test_update@gmail.com", "99999");

        when(myModelRepository.findById(id)).thenReturn(Optional.of(mockExistUser));
        when(myModelRepository.save(any(MyModel.class))).thenReturn(mockUpdatedUser);

        MyModel result = myService.updateUser(id,mockRequest);

        Assertions.assertThat(result).isNotNull().isEqualTo(mockUpdatedUser);
        Assertions.assertThat(result.getId()).isNotNull().isEqualTo(1L);
        Assertions.assertThat(result.getName()).isNotNull().isEqualTo("Test Update");

        verify(myModelRepository,times(1)).findById(id);
        verify(myModelRepository,times(1)).save(any(MyModel.class));
    }

    //Delete User
    @Test
    void test_deleteUser_ShouldReturnVoid() {
        long id = 1;

        doNothing().when(myModelRepository).deleteById(id);

        myService.deleteUser(id);

        verify(myModelRepository,times(1)).deleteById(id);
    }

}