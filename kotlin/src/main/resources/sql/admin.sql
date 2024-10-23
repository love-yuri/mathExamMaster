DROP DATABASE IF EXISTS zyl;
CREATE DATABASE zyl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use zyl;

# CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci

DROP TABLE IF EXISTS `user`;
CREATE TABLE user (
  `id` bigint NOT NULL COMMENT 'id',
  `role` tinyint(1) NOT NULL default 1 comment '角色: 1: 管理员, 2: 老师， 3: 学生',
  `user_name` varchar(16) NOT NULL unique comment '用户名',
  `pass_word` varchar(128) NOT NULL comment '密码',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) NOT NULL comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) NOT NULL  comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

insert into user value (1, 1, 'yuri', '$2a$10$b2nCP59gVbx1Eqhw6c2p2OS6jlbX.8GrjziRuGNjTQ.1lKuU5y3f.', 0, NOW(), 1, NOW(), 1);

DROP TABLE IF EXISTS `system_file`;
CREATE TABLE `system_file` (
  `id` bigint NOT NULL COMMENT '文件id',
  `type` tinyint(2) NOT NULL DEFAULT 0 comment '文件类型: 默认其他类型',
  `filename` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '文件名',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '路径',
  `md5` varchar(32) NOT NULL comment '文件md5值',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) NOT NULL comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) NOT NULL comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文件列表';

DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank` (
   `id` bigint NOT NULL COMMENT '主键id',
   `type` tinyint(1) NOT NULL comment '题目类型',
   `content` text NOT NULL comment '题目内容',
   `answer` json NOT NULL comment '答案',
   `difficulty` tinyint(1) NOT NULL DEFAULT 5 comment '难度: 默认5,最高9',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='题库';

DROP TABLE IF EXISTS `knowledge_point`;
CREATE TABLE `knowledge_point` (
 `id` bigint NOT NULL COMMENT '主键id',
 `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '知识点名称',
 `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL comment '知识点描述',
 `parent` bigint NOT NULL DEFAULT 0 comment '父级知识点: 0 代表根知识',
 `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 `create_by` varchar(16) NOT NULL comment '创建用户',
 `update_time` datetime NOT NULL COMMENT '最后修改时间',
 `update_by` varchar(16) NOT NULL comment '更新用户',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识点';

DROP TABLE IF EXISTS `bank_and_point`;
CREATE TABLE `bank_and_point` (
   `id` bigint NOT NULL COMMENT '主键id',
   `question_bank_id` bigint NOT NULL COMMENT '题目id',
   `knowledge_point_id` bigint NOT NULL COMMENT '知识点id',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识点-题目关联表';