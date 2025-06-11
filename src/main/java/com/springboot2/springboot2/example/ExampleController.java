package com.springboot2.springboot2.example;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/example")
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping()
    public ResponseEntity<List<ExampleModel>> getAll() {
        List<ExampleModel> data = exampleService.getAllData();
        return ResponseEntity.status(224).body(data);
    }

    @PostMapping()
    public ResponseEntity<ExampleModel> create(@RequestBody ExampleRequest request) {
        ExampleModel data1 = exampleService.createData(request);
        return ResponseEntity.status(240).body(data1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExampleModel> update(@PathVariable(name = "id") long id, @RequestBody ExampleModel request) throws BadRequestException {
        ExampleModel data1 = exampleService.updateData(id, request);
        return ResponseEntity.status(240).body(data1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>   delete(@PathVariable(name="id") long id){
       exampleService.deleteData(id);
       return ResponseEntity.status(240).body("Delete Success");
    }
}
