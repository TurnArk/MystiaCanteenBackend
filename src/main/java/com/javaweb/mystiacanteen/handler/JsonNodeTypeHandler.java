package com.javaweb.mystiacanteen.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        try {
            // 将 JsonNode 转换为字符串并设置到 PreparedStatement 中
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (IOException e) {
            throw new SQLException("Failed to serialize JsonNode", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            // 从 ResultSet 中读取字符串并转换为 JsonNode
            String json = rs.getString(columnName);
            return json != null ? objectMapper.readTree(json) : null;
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JsonNode", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            // 从 ResultSet 中读取字符串并转换为 JsonNode
            String json = rs.getString(columnIndex);
            return json != null ? objectMapper.readTree(json) : null;
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JsonNode", e);
        }
    }

    @Override
    public JsonNode getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        try {
            // 从 CallableStatement 中读取字符串并转换为 JsonNode
            String json = cs.getString(columnIndex);
            return json != null ? objectMapper.readTree(json) : null;
        } catch (IOException e) {
            throw new SQLException("Failed to deserialize JsonNode", e);
        }
    }
}
