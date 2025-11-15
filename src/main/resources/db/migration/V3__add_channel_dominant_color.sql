-- \src\main\resources\db\migration\V3__add_channel_dominant_color.sql
ALTER TABLE channel
    ADD dominant_color CHAR(7) NULL COMMENT '主色调（HEX格式，如 #FF5733）' AFTER background