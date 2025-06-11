package com.springboot2.springboot2.my;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MyModelRepository myModelRepository;

    public List<MyModel> getAllUsers() {
        return myModelRepository.findAll();
    }

    public MyModel getUserByEmail(String email) throws EntityNotFoundException {
        return myModelRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }

    public MyModel getAllUserById(Long id) throws EntityNotFoundException {
        return myModelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public MyModel createUser(MyRequest req) {
        MyModel user = MyModel.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .build();
        return myModelRepository.save(user);
    }

    public MyModel updateUser(long id, MyRequest req) throws EntityNotFoundException {
        return myModelRepository.findById(id)
                .map(myModel -> myModelRepository.save(
                        MyModel.builder()
                                .id(myModel.getId())
                                .name(req.getName())
                                .email(req.getEmail())
                                .phone(req.getPhone())
                                .build()))
                .orElseThrow(EntityNotFoundException::new);
    }


    public void deleteUser(long id){
        myModelRepository.deleteById(id);
    }
}
