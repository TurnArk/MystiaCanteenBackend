package com.javaweb.mystiacanteen.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TalkMessage {
    private int id;
    private String username;
    private String message;
    private String talk_with;
    private int productionId;
    private String type;
    private LocalDateTime time;
}
