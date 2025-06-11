package com.springboot2.springboot2.my;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class MyController {
    private final MyService myService;

    @GetMapping()
    public ResponseEntity<List<MyModel>> getAllUsers() {
        List<MyModel> allUsers = myService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<MyModel> getUserByParamEmail(@RequestParam(name = "email") String email) throws EntityNotFoundException {
        return ResponseEntity.ok(myService.getUserByEmail(email));
    }

    @GetMapping("/byId")
    public ResponseEntity<MyModel> getUserByHeaderId(@RequestHeader(name = "id") Long id) throws EntityNotFoundException {
        System.out.println("id: " + id);
        return ResponseEntity.ok(myService.getAllUserById(id));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }


    @PostMapping()
    public ResponseEntity<MyModel> createUser(@RequestBody MyRequest req){
        MyModel user = myService.createUser(req);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyModel> updateUser(@PathVariable(name="id") long id, @RequestBody MyRequest req){
        MyModel user = myService.updateUser(id,req);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUser(@PathVariable(name="id") long id){
        myService.deleteUser(id);
        return  ResponseEntity.ok("The user has been deleted successfully.");
    }

}
