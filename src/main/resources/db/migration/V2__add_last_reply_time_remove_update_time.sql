-- V2__article_add_last_reply_time_drop_update_time.sql
--
-- 日期：2025-10-30
-- 需求：用户发送评论后能让文章表记录文章最后回复时间
--
-- 变更说明：
--   1. 新增 last_reply_time 字段，用于记录文章最后回复时间
--   2. 删除冗余的 update_time 字段（业务已不再使用）


ALTER TABLE article
    ADD last_reply_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '最后回复时间' AFTER floor_count;

-- 删除旧的时间戳字段
ALTER TABLE article
    DROP COLUMN update_time;