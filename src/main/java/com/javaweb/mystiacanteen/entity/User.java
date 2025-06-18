package com.javaweb.mystiacanteen.entity;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String name;
    private String gender;
    private String email;
    private Integer deposit;
}
