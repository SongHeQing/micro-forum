package com.zqg.communityservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 数据迁移运行器 - 用于执行数据库迁移任务
 */
@SpringBootTest(classes = DatabaseMigrationTool.class)
public class MigrationRunner {

    @Test
    public void runDatabaseMigration() {
        // 创建迁移工具实例并执行迁移
        DatabaseMigrationTool tool = new DatabaseMigrationTool();
        tool.migrateMediaFieldToJSONArray();
    }
}