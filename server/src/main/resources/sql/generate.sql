SELECT
    COLUMN_NAME AS `field`,
    COLUMN_TYPE AS `type`,
    COLUMN_COMMENT AS `comment`,
    TABLE_COMMENT AS `table_comment`,
    COLUMN_DEFAULT AS `default_value`,
    IS_NULLABLE AS is_nullable
FROM
    INFORMATION_SCHEMA.COLUMNS as col
        left join INFORMATION_SCHEMA.TABLES as tab on col.TABLE_NAME = tab.TABLE_NAME
WHERE
    col.TABLE_SCHEMA = 'zyl' AND col.TABLE_NAME = 'user' AND
    tab.TABLE_SCHEMA = 'zyl' AND tab.TABLE_NAME = 'user';


select * from question_bank;

INSERT INTO user VALUE (11, 3, 'alice', 'Alice', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (2, 3, 'bob', 'Bob', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (3, 3, 'charlie', 'Charlie', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (4, 3, 'david', 'David', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (5, 3, 'emma', 'Emma', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (6, 3, 'frank', 'Frank', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (7, 3, 'grace', 'Grace', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (8, 3, 'henry', 'Henry', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (9, 3, 'ivy', 'Ivy', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
INSERT INTO user VALUE (10, 3, 'jack', 'Jack', 'pass1234', FALSE, NOW(), 'yuri', NOW(), 'yuri');
