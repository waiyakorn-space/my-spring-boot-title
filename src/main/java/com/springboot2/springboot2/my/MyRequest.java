package com.springboot2.springboot2.my;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyRequest {
    private String name;
    private  String email;
    private  String phone;
}
