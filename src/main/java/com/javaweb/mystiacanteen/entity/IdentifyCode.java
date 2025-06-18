package com.javaweb.mystiacanteen.entity;

import lombok.Data;

@Data
public class IdentifyCode {
    private int id;
    private String email;
    private String code;
    private long start_time;
    private long out_time;
}
