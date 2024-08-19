DROP TABLE IF EXISTS `user`;
CREATE TABLE user (
  `id` bigint NOT NULL COMMENT 'id',
  `role` tinyint(1) NOT NULL default 1 comment '角色: 1: 管理员, 2: 老师， 3: 学生',
  `pass_word` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '密码',
  `deleted` boolean NOT NULL DEFAULT FALSE comment '是否删除',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

