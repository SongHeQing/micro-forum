-- 重命名article表的media_urls字段为media
ALTER TABLE article RENAME COLUMN media_urls TO media;

-- 重命名article_collection_previews表的first_image_url字段为first_image
ALTER TABLE article_collection_previews RENAME COLUMN first_image_url TO first_image;

-- 为article_collection_previews表添加article_id字段
ALTER TABLE article_collection_previews ADD COLUMN article_id INT UNSIGNED NULL COMMENT '文章ID (与article.id相同)';

-- 添加索引
CREATE INDEX IX_acp_article_id ON article_collection_previews (article_id);
