DROP DATABASE IF EXISTS zyl;
CREATE DATABASE zyl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use zyl;

# CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci

DROP TABLE IF EXISTS `user`;
CREATE TABLE user (
  `id` bigint NOT NULL COMMENT 'id',
  `role` tinyint(1) NOT NULL default 3 comment '角色: 1: 管理员, 2: 老师， 3: 学生',
  `user_name` varchar(16) NOT NULL unique comment '用户名',
  `nick_name` varchar(32) NOT NULL comment '昵称',
  `pass_word` varchar(16) NOT NULL comment '密码',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) NOT NULL comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) NOT NULL  comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

# 默认管理
insert into user value (1, 1, 'yuri', 'yuri', 'yuri', 0, NOW(), 'yuri', NOW(), 'yuri');

DROP TABLE IF EXISTS `user_department`;
CREATE TABLE user_department (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL comment '用户id',
  `department_id` bigint NOT NULL comment '组织id',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) NOT NULL comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) NOT NULL  comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-组织关联表';

DROP TABLE IF EXISTS `department`;
CREATE TABLE department (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(64) NOT NULL comment '组织名称',
  `teacher_id` bigint NULL DEFAULT NULL comment '老师用户id',
  `parent_id` bigint NULL DEFAULT NULL comment '父组织id',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(16) NOT NULL comment '创建用户',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  `update_by` varchar(16) NOT NULL  comment '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织-部门表';
insert into department value (1, '常熟理工学院', NULL, null,0, NOW(), 'yuri', NOW(), 'yuri');

DROP TABLE IF EXISTS `system_file`;
CREATE TABLE `system_file` (
  `id` bigint NOT NULL COMMENT '文件id',
  `type` tinyint(2) NOT NULL DEFAULT 0 comment '文件类型: 默认其他类型',
  `filename` varchar(128) NOT NULL comment '文件名',
  `path` varchar(128) NOT NULL comment '路径',
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
 `name` varchar(128) NOT NULL comment '知识点名称',
 `description` varchar(128) NULL comment '知识点描述',
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

DROP TABLE IF EXISTS `exam_page`;
CREATE TABLE `exam_page` (
 `id` bigint NOT NULL COMMENT '试卷的id',
 `title` varchar(255) NOT NULL COMMENT '试卷的标题',
 `subject` tinyint(1) NOT NULL DEFAULT 0 COMMENT '试卷科目，默认0: 高等数学',
 `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '试卷的类型，默认0: 普通试卷',
 `difficulty` tinyint(1) NOT NULL DEFAULT 5 COMMENT '试卷的难度，默认5，最高9',
 `limited_time` int NOT NULL DEFAULT 7200 COMMENT '试卷限时（秒），默认7200秒（2小时）',
 `total_score` int NOT NULL DEFAULT 100 COMMENT '试卷总分，默认100',
 `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 `create_by` varchar(16) NOT NULL comment '创建用户',
 `update_time` datetime NOT NULL COMMENT '最后修改时间',
 `update_by` varchar(16) NOT NULL comment '更新用户',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='试卷表';

DROP TABLE IF EXISTS `exam_page_question_relation`;
CREATE TABLE `exam_page_question_relation` (
   `id` bigint NOT NULL COMMENT '主键id',
   `exam_page_id` bigint NOT NULL COMMENT '试卷id',
   `question_bank_id` bigint NOT NULL COMMENT '题库id',
   `score` int NOT NULL COMMENT '该题得分',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='试卷-题目关联表';

DROP TABLE IF EXISTS `exam_page_release`;
CREATE TABLE `exam_page_release` (
   `id` bigint NOT NULL COMMENT '主键id',
   `class_id` bigint NOT NULL COMMENT '发布班级id',
   `exam_page_id` bigint NOT NULL COMMENT '试卷id',
   `start_time` datetime NOT NULL COMMENT '开始时间',
   `end_time` datetime NOT NULL COMMENT '结束时间',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='试卷-发布表';

DROP TABLE IF EXISTS `exam_page_user_relation`;
CREATE TABLE `exam_page_user_relation` (
   `id` bigint NOT NULL COMMENT '主键id',
   `page_release_id` bigint NOT NULL COMMENT '试卷发布id',
   `user_id` bigint NOT NULL COMMENT '用户id',
   `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '当前试卷的状态，默认0: 未完成',
   `exam_start_time` datetime NULL DEFAULT NULL COMMENT '开始练习时间',
   `answer` JSON NULL DEFAULT NULL COMMENT '用户答案',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='试卷-学生关联表';


DROP TABLE IF EXISTS `user_score`;
CREATE TABLE `user_score` (
   `id` bigint NOT NULL COMMENT '主键id',
   `page_release_id` bigint NOT NULL COMMENT '试卷发布id',
   `user_id` bigint NOT NULL COMMENT '用户id',
   `score` int NOT NULL DEFAULT 0 COMMENT '用户得分',
   `total_score` int NOT NULL DEFAULT 100 COMMENT '试卷总分',
   `detail` JSON NULL DEFAULT NULL COMMENT '用户得分详情',
   `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
   `create_time` datetime NOT NULL COMMENT '创建时间',
   `create_by` varchar(16) NOT NULL comment '创建用户',
   `update_time` datetime NOT NULL COMMENT '最后修改时间',
   `update_by` varchar(16) NOT NULL comment '更新用户',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户得分记录表';