-- 为频道表添加创建人字段，初始允许 NULL
ALTER TABLE channel 
    ADD COLUMN creator_id int unsigned NULL COMMENT '创建人ID' AFTER detail;

-- 为现有记录填充默认值（假设默认值为 0，表示系统创建）
UPDATE channel SET creator_id = 0 WHERE creator_id IS NULL;

-- 修改字段为 NOT NULL
ALTER TABLE channel 
    MODIFY COLUMN creator_id int unsigned NOT NULL COMMENT '创建人ID';

-- 添加创建人ID索引，便于查询某个用户创建的所有频道
CREATE INDEX idx_channel_creator_id ON channel (creator_id);

-- 添加创建人ID和创建时间的复合索引，便于按时间查询某个用户创建的频道
CREATE INDEX idx_channel_creator_time ON channel (creator_id, create_time);