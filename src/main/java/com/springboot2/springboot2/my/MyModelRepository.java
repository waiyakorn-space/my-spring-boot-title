package com.springboot2.springboot2.my;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyModelRepository extends JpaRepository<MyModel,Long> {
    Optional<MyModel> findByEmail(String email);
}


