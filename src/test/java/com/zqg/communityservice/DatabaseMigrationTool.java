package com.zqg.communityservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;

/**
 * 数据库迁移工具类 - 直接在代码中配置数据库连接
 * 用于将article表中的media字段从空格分隔改为JSON数组格式
 */
@SpringBootApplication
@Configuration
@EnableTransactionManagement
@Component
public class DatabaseMigrationTool {

    // 数据库连接信息 - 替换为你的实际数据库信息
    private static final String DB_URL = "jdbc:mysql://192.168.101.39:3306/micro-forum?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000";
    private static final String DB_USERNAME = "root"; // 请替换为你的数据库用户名
    private static final String DB_PASSWORD = "";

    /**
     * 配置数据源
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    /**
     * 配置JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Article实体类简化版，仅用于数据迁移
     */
    static class Article {
        private Long id;
        private String media;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }

    static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) {
            Article article = new Article();
            try {
                article.setId(rs.getLong("id"));
                article.setMedia(rs.getString("media"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return article;
        }
    }

    /**
     * 执行数据迁移
     */
    @Transactional
    public void migrateMediaFieldToJSONArray() {
        System.out.println("开始执行数据迁移：将media字段从空格分隔改为JSON数组格式");

        // 获取JdbcTemplate实例
        JdbcTemplate template = jdbcTemplate(dataSource());

        try {
            // 测试数据库连接
            template.execute("SELECT 1");
            System.out.println("数据库连接成功！");
        } catch (Exception e) {
            System.err.println("数据库连接失败，请检查用户名、密码和权限设置：" + e.getMessage());
            return;
        }

        // 查询所有有媒体数据的文章
        String sql = "SELECT id, media FROM article WHERE media IS NOT NULL AND media != ''";
        java.util.List<Article> articles = template.query(sql, new ArticleRowMapper());

        System.out.println("找到 " + articles.size() + " 条需要转换的记录");

        int convertedCount = 0;
        for (Article article : articles) {
            if (article.getMedia() != null && !article.getMedia().trim().isEmpty()) {
                // 将空格分隔的字符串转换为JSON数组
                String[] mediaUrls = article.getMedia().trim().split("\\s+");

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonArray = objectMapper.writeValueAsString(mediaUrls);

                    // 更新数据库
                    String updateSql = "UPDATE article SET media = ? WHERE id = ?";
                    template.update(updateSql, jsonArray, article.getId());

                    convertedCount++;
                    if (convertedCount % 100 == 0) {
                        System.out.println("已转换 " + convertedCount + " 条记录");
                    }
                } catch (JsonProcessingException e) {
                    System.err.println("转换文章ID " + article.getId() + " 的媒体数据时出错: " + e.getMessage());
                }
            }
        }

        System.out.println("数据迁移完成！共转换了 " + convertedCount + " 条记录");
    }

    /**
     * 主方法，用于运行数据迁移
     */
    public static void main(String[] args) {
        DatabaseMigrationTool tool = new DatabaseMigrationTool();
        tool.migrateMediaFieldToJSONArray();
    }
}