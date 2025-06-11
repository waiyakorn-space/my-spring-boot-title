package com.springboot2.springboot2.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleModelRepository extends JpaRepository<ExampleModel,Long> {

}
