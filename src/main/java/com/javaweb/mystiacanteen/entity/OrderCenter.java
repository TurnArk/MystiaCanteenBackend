package com.javaweb.mystiacanteen.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class OrderCenter {
    private String username;
    private JsonNode message;
}
