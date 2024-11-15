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

insert into user values (2, 0, 'alice', 'alice', 0, NOW(), 1, NOW(), 1);
insert into user values (3, 0, 'bob', 'bob', 0, NOW(), 1, NOW(), 1);
insert into user values (4, 0, 'charlie', 'charlie', 0, NOW(), 1, NOW(), 1);
insert into user values (5, 0, 'diana', 'diana', 0, NOW(), 1, NOW(), 1);
insert into user values (6, 0, 'eve', 'eve', 0, NOW(), 1, NOW(), 1);
insert into user values (7, 0, 'frank', 'frank', 0, NOW(), 1, NOW(), 1);
insert into user values (8, 0, 'grace', 'grace', 0, NOW(), 1, NOW(), 1);
insert into user values (9, 0, 'hank', 'hank', 0, NOW(), 1, NOW(), 1);
insert into user values (10, 0, 'iris', 'iris', 0, NOW(), 1, NOW(), 1);
