package com.songheqing.microforum.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MyBatis TypeHandler：字符串与List<String>之间的转换
 * 
 * 作用：将数据库中存储的空格分隔的字符串转换为List<String>
 * 例如：数据库存储 "url1 url2 url3" -> List<String> ["url1", "url2", "url3"]
 * 
 * @author 宋和清
 */
@MappedTypes(List.class) // 告诉MyBatis这个TypeHandler处理List类型
public class StringToListHandler extends BaseTypeHandler<List<String>> {

    /**
     * 将List<String>转换为字符串存储到数据库
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
        // 将List<String>用空格连接成字符串
        String value = String.join(" ", parameter);
        ps.setString(i, value);
    }

    /**
     * 从ResultSet中获取字符串并转换为List<String>
     * 当执行SELECT查询时，MyBatis会调用这个方法
     * 
     * @param rs         ResultSet对象
     * @param columnName 列名
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return stringToList(value);
    }

    /**
     * 从ResultSet中获取字符串并转换为List<String>
     * 当执行SELECT查询时，MyBatis会调用这个方法
     * 
     * @param rs          ResultSet对象
     * @param columnIndex 列索引
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return stringToList(value);
    }

    /**
     * 从CallableStatement中获取字符串并转换为List<String>
     * 当执行存储过程时，MyBatis会调用这个方法
     * 
     * @param cs          CallableStatement对象
     * @param columnIndex 列索引
     * @return List<String>
     */
    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return stringToList(value);
    }

    /**
     * 将字符串转换为List<String>的辅助方法
     * 
     * @param value 数据库中的字符串值
     * @return List<String>，如果输入为null或空字符串则返回空列表
     */
    private List<String> stringToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        // 按空格分割字符串
        return Arrays.asList(value.split(" "));
    }
}