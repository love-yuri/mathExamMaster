DROP DATABASE IF EXISTS zyl;
CREATE DATABASE zyl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use zyl;

DROP TABLE IF EXISTS `user`;
CREATE TABLE user (
  `id` bigint NOT NULL COMMENT 'id',
  `role` tinyint(1) NOT NULL default 1 comment '角色: 1: 管理员, 2: 老师， 3: 学生',
  `user_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci unique comment '用户名',
  `pass_word` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '密码',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci unique comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci unique comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

insert into user value (1, 1, 'yuri', '$2a$10$b2nCP59gVbx1Eqhw6c2p2OS6jlbX.8GrjziRuGNjTQ.1lKuU5y3f.', 0, NOW(), 1, NOW(), 1);

SELECT
    TABLE_COMMENT
FROM
    INFORMATION_SCHEMA.TABLES
WHERE
    TABLE_SCHEMA = 'zyl'
  AND TABLE_NAME = 'user';

SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'zyl' AND TABLE_NAME = 'user';

SELECT
    COLUMN_NAME AS `field`,
    COLUMN_TYPE AS `type`,
    COLUMN_COMMENT AS `comment`,
    TABLE_COMMENT AS `table_comment`
FROM
    INFORMATION_SCHEMA.COLUMNS as col
    left join INFORMATION_SCHEMA.TABLES as tab on col.TABLE_NAME = tab.TABLE_NAME
WHERE
    col.TABLE_SCHEMA = 'zyl' AND col.TABLE_NAME = 'user' AND
    tab.TABLE_SCHEMA = 'zyl' AND tab.TABLE_NAME = 'user';

select * from user;