package com.songheqing.microforum.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis TypeHandler：JSON字符串与List<String>之间的转换
 * 
 * 作用：将数据库中存储的JSON格式字符串转换为List<String>
 * 例如：数据库存储 "["url1", "url2", "url3"]" -> List<String> ["url1", "url2", "url3"]
 * 
 * @author 宋和清
 */
@MappedTypes(List.class)
public class JsonToListHandler extends BaseTypeHandler<List<String>> {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonToListHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将List<String>转换为JSON字符串存储到数据库
     * 当执行INSERT或UPDATE时，MyBatis会调用这个方法
     * 
     * @param ps        PreparedStatement对象
     * @param i         参数位置
     * @param parameter List<String>参数
     * @param jdbcType  JDBC类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            String jsonValue = objectMapper.writeValueAsString(parameter);
            ps.setString(i, jsonValue);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to convert List to JSON string", e);
        }
    }

    /**
     * 从ResultSet中获取JSON字符串并转换为List<String>
     * 当执行SELECT查询时，MyBatis会调用这个方法
     * 
     * @param rs         ResultSet对象
     * @param columnName 列名
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonValue = rs.getString(columnName);
        return jsonToList(jsonValue);
    }

    /**
     * 从ResultSet中获取JSON字符串并转换为List<String>
     * 当执行SELECT查询时，MyBatis会调用这个方法
     * 
     * @param rs          ResultSet对象
     * @param columnIndex 列索引
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonValue = rs.getString(columnIndex);
        return jsonToList(jsonValue);
    }

    /**
     * 从CallableStatement中获取JSON字符串并转换为List<String>
     * 当执行存储过程时，MyBatis会调用这个方法
     * 
     * @param cs          CallableStatement对象
     * @param columnIndex 列索引
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonValue = cs.getString(columnIndex);
        return jsonToList(jsonValue);
    }

    /**
     * 将JSON字符串转换为List<String>的辅助方法
     * 
     * @param jsonValue 数据库中的JSON字符串值
     * @return List<String>，如果输入为null或解析失败则返回空列表
     */
    private List<String> jsonToList(String jsonValue) throws SQLException {
        if (jsonValue == null || jsonValue.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(jsonValue, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            logger.warn("Failed to parse JSON string: {}", jsonValue, e);
            return new ArrayList<>();
        }
    }
}