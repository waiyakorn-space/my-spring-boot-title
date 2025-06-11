package com.springboot2.springboot2.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExampleRequest {
    private String name;
    private  String email;
    private  String phone;
}
