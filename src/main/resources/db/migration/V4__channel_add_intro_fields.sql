-- \src\main\resources\db\migration\V4__channel_add_intro_fields.sql
-- 添加频道简介和详细简介字段
ALTER TABLE channel
    ADD description VARCHAR(20) NULL COMMENT '频道简介' AFTER dominant_color,
    ADD detail VARCHAR(500) NULL COMMENT '详细简介' AFTER description;
-- 删除更新时间字段
ALTER TABLE channel
    DROP COLUMN update_time;
-- 修改频道主职位字段
alter table channel_owner
    modify type tinyint unsigned not null comment '频道主职位, 1:频道主, 2:频道管理员, 3:板块管理员';