create table if not exists article
(
    id              int unsigned auto_increment comment 'ID,主键'
        primary key,
    user_id         int unsigned                           not null comment '用户ID',
    channel_id      int unsigned                           not null comment '频道ID',
    title           varchar(31)                            not null comment '标题',
    content_preview varchar(300)                           null comment '正文预览',
    content         varchar(2000)                          null comment '完整的文章内容，最大2000字符',
    media_type      tinyint unsigned                       null comment '文章媒体类型：NULL=无；1=图片；2=视频',
    media_urls      varchar(400)                           null comment '图片',
    like_count      int unsigned default '0'               not null comment '点赞量',
    collect_count   int unsigned default '0'               not null comment '收藏量',
    comment_count   int unsigned default '0'               not null comment '回复（评论）量',
    view_count      int unsigned default '0'               not null comment '文章点击量/阅读量',
    floor_count     int unsigned default '0'               null comment '文章的一级评论楼层计数',
    create_time     datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '文章表';

create index article_user_id_index
    on article (user_id);

create index channel_id
    on article (channel_id);

create index idx_article_create_time
    on article (create_time);

create table if not exists article_collection_previews
(
    id              int unsigned                        not null comment '文章ID (与article.id相同，作为预览记录的主键)'
        primary key,
    author_user_id  int unsigned                        not null comment '文章作者用户ID',
    title           varchar(255)                        not null comment '文章标题',
    first_image_url varchar(255)                        null comment '文章第一张图片URL (封面图)',
    collected_count int       default 0                 null comment '被收藏的数量，用于管理本记录的生命周期',
    create_time     timestamp default CURRENT_TIMESTAMP null comment '记录创建时间 (首次被收藏时)'
)
    comment '被收藏文章的精简摘要信息表';

create index IX_acp_author_id
    on article_collection_previews (author_user_id);

create index IX_acp_collected_count
    on article_collection_previews (collected_count);

create table if not exists article_likes
(
    id          int unsigned auto_increment comment '点赞记录ID'
        primary key,
    user_id     int unsigned                       not null comment '点赞用户ID',
    article_id  int unsigned                       not null comment '被点赞的文章ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '点赞时间',
    constraint UQ_user_article_like
        unique (user_id, article_id)
)
    comment '文章点赞记录表';

create index IX_article_likes_article_id
    on article_likes (article_id, create_time);

create index IX_article_likes_user_id
    on article_likes (user_id, create_time);

create table if not exists channel
(
    id            int unsigned auto_increment comment 'ID,主键'
        primary key,
    channelname   varchar(96)                            not null comment '频道名',
    image         varchar(255)                           null comment '频道封面',
    background    varchar(255)                           null comment '频道背景',
    user_count    int unsigned default '0'               null comment '用户数量',
    article_count int unsigned default '0'               null comment '文章数量',
    create_time   datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint username
        unique (channelname)
)
    comment '频道表';

create table if not exists channel_owner
(
    id          int unsigned auto_increment comment 'ID,主键'
        primary key,
    channel_id  int unsigned                       not null comment '频道ID',
    user_id     int unsigned                       not null comment '用户ID',
    type        tinyint unsigned                   not null comment '频道主职位, 1:频道主, 2:频道管理员, 3:板块管理员，4:板块管理员',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '频道主';

create table if not exists comment
(
    id               int unsigned auto_increment comment '主键 ID'
        primary key,
    article_id       int unsigned                           not null comment '所属文章 ID',
    parent_id        int unsigned                           null comment '父评论 ID，一级评论为空',
    floor            int unsigned                           null comment '楼层号，仅一级评论有值',
    user_id          int unsigned                           not null comment '评论所属用户 ID',
    reply_count      int unsigned default '0'               null comment '二级评论数',
    reply_to_user_id int unsigned                           null comment '回复目标用户 ID（用于 UI 展示）',
    like_count       int unsigned default '0'               null comment '点赞数',
    content          varchar(2000)                          not null comment '评论内容',
    create_time      datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create index comment_user_id_index
    on comment (user_id);

create index idx_article_id_parent_id_create_time
    on comment (article_id, parent_id, create_time);

create index idx_parent_id_create_time
    on comment (parent_id, create_time);

create table if not exists comment_likes
(
    id          int unsigned auto_increment comment '评论点赞记录ID'
        primary key,
    user_id     int unsigned                       not null comment '点赞用户ID',
    article_id  int unsigned                       not null comment '所属文章ID（冗余，方便查询）',
    comment_id  int unsigned                       not null comment '被点赞的评论ID',
    create_time datetime default CURRENT_TIMESTAMP null comment '点赞时间',
    constraint UQ_user_comment_like
        unique (user_id, comment_id)
)
    comment '评论点赞记录表';

create index IX_comment_likes_comment_id
    on comment_likes (comment_id, create_time);

create index IX_comment_likes_user_id
    on comment_likes (user_id, create_time);

create table if not exists images
(
    id          int unsigned auto_increment comment '图片ID,主键'
        primary key,
    image_url   varchar(255)                               not null comment '图片的存储路径/URL',
    entity_type varchar(50)                                not null comment '关联的实体类型（例如 "ARTICLE", "USER", "CHANNEL", "COMMENT"）',
    entity_id   int unsigned                               not null comment '关联的业务ID（例如文章ID、用户ID、频道ID、评论ID）',
    order_num   tinyint unsigned default '0'               not null comment '图片排序（针对多图实体，如文章的图片列表顺序）',
    create_time datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '通用图片表';

create index entity_type_entity_id_order_num_index
    on images (entity_type, entity_id, order_num);

create table if not exists user
(
    id                   int unsigned auto_increment comment 'ID ,主键'
        primary key,
    phone                char(11)                     null comment '手机号',
    email                char(255)                    not null comment '邮箱',
    password             varchar(16)                  not null comment '密码',
    nickname             varchar(20)                  not null comment '昵称',
    image                varchar(255)                 null comment '头像',
    article_send_count   int unsigned default '0'     not null comment '文章发送量',
    comment_send_count   int unsigned default '0'     not null comment '评论发送量',
    channel_follow_count int unsigned default '0'     not null comment '频道关注量',
    follow_count         int unsigned default '0'     not null comment '用户关注量',
    fans_count           int unsigned default '0'     not null comment '粉丝数量',
    like_count           int unsigned default '0'     not null comment '获赞量',
    introduction         varchar(500)                 null comment '个人简介',
    create_time          datetime     default (now()) null comment '创建时间',
    constraint email
        unique (email),
    constraint phone
        unique (phone),
    constraint username
        unique (nickname)
)
    comment '用户表';

create index user_email_password_index
    on user (email, password);

create table if not exists user_collections
(
    id                 int unsigned auto_increment comment '收藏关系ID'
        primary key,
    user_id            int unsigned                       not null comment '收藏用户ID',
    article_preview_id int unsigned                       not null comment '收藏的文章预览ID（关联article_collection_previews.id）',
    create_time        datetime default CURRENT_TIMESTAMP null comment '收藏时间',
    constraint UQ_user_article_collection
        unique (user_id, article_preview_id)
)
    comment '用户收藏关系表';

create index IX_user_collections_article_preview_id
    on user_collections (article_preview_id);

create index IX_user_collections_user_id
    on user_collections (user_id, create_time);

create table if not exists user_follows
(
    id           int unsigned auto_increment comment '关注记录ID'
        primary key,
    follower_id  int unsigned                       not null comment '关注者用户ID',
    following_id int unsigned                       not null comment '被关注者用户ID',
    create_time  datetime default CURRENT_TIMESTAMP null comment '关注时间',
    constraint UQ_follower_following
        unique (follower_id, following_id)
)
    comment '用户关注关系表';

create index IX_user_follows_follower_id
    on user_follows (follower_id, create_time);

create index IX_user_follows_following_id
    on user_follows (following_id, create_time);


