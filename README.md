# 高等数学考试系统

## 题库设计

> 对于每一道题目都有完整的信息关联，比如知识点，题型，所属章节之类的

1. 科目类型: 高等数学/线性代数
2. 题目类型：单选/多选/判断/简单填空/复杂
3. 知识点：关联表 --> 知识点表

## 表结构完善

- [x] 试卷表
  - `id` 试卷的id
  - `title` 试卷的标题
  - `subject` 试卷科目 默认0: 高等数学
  - `type` 试卷的类型 默认0： 普通试卷
  - `difficulty` 试卷的难度: 默认5 最高9
  - `limited_time` 试卷限时： 默认7200 2消失
  - `total_score` 试卷总分: 默认100
- [x] 试卷-题目关联表
  - `id` 主键id
  - `exam_page_id` 试卷id
  - `question_bank_id` 题库id
  - `score` 该题得分
- [x] 试卷-学生关联表
  - `id` 主键id
  - `exam_page_id` 试卷id
  - `user_id` 用户id
  - `status` 当前试卷的状态，默认0: 未完成
  - `deadline` 截止时间： 默认null 没有截止时间

## 功能完善

### 试卷创建

- [x] 创建试卷
  - [ ] 试卷创建支持练习/考试模式
- [ ] 随机抽题
- [ ] 根据难度随机抽题
- [ ] 根据知识点随机抽题

### 题目创建

- [ ] 创建时缺少科目选择
- [ ] 创建时缺少解析
- [ ] 填空题可以在编辑器中插入填空(下划线填空)
- [ ] 创建时预览界面

### 其他功能

- [ ] 学科管理
- [x] 人员管理
- [ ] 任务管理

## 页面优化

- [ ] 深色浅色主题变化-取消or保留？
- [ ] 页面布局优化，有些功能是否需要？
- [ ] 菜单路由优化
- [ ] 部分数据是否需要禁止删除?

## Sql查询优化

- [ ] 检查练习状态返回数据不需要多次查询
- [ ] 用户名 -> 用户id使用redis查询
- [ ] 题目结果json解析 -> 使用redis解析

## Bug修复

- [x] 开始考试后界面的倒计时为`00:00:00`
- [x] word无法粘贴图片文字
- [ ] 插入的公式无法选择在文字中间，只能是文字旁边

## feature

> 未来1一个月的开发需求 - 2.10

- [x] 发布试卷名称和试卷名称是否需要统一?
- [x] 组织架构需要添加为组织设置老师的功能
- [x] 发布试卷需要支持按照整个班级发布
- [x] 试卷发布单位需要改为班级（目前是个人）
- [x] 老师账户需要和班级相关联防止对别的班级布置任务
- [ ] ~~老师登陆后需要支持查看自己的班级~~
- [ ] ~~同一份试卷未完成是否可以发布两次~~ (老师自行安排)
- [x] 组织管理是否支持自定义（目前支持）
- [ ] 创建试卷时需要按照题型进行排序
- [x] 创建题目时的排版？
- [x] 解决wps/word不能一次性复制多个数据的问题
- [ ] ~~是否需要支持老师单独发布作业到某个学生?~~ 
- [ ] ~~试卷发布是否需要支持考试(禁止查看练习模式数据)/练习模式~~ 
- [ ] 已经开始考试的试卷禁止修改
- [ ] 发布试卷的班级支持多选班级
- [ ] 已发布试卷列表新增已结束的考试和未结束的考试

  - [ ] ~~可以直接结束考试~~ 

  - [x] 选择填空 自动批卷(先出结果 再由老师选择是否应用)
  - [x] 客观题手动批卷
  - [ ] 批卷结束后是否需要标识一下
- [ ] 批改完成的卷子需要支持查看详情

  - [ ] 学生成绩分布
  - [ ] 每道题目提分率
  - [ ] 班级平均分/中位数等信息
- [ ] 老师是否有权利查看组织架构 
- [ ] 考试结束后需要手动处理用户退出
- [ ] editor需要区分是否函数img的粘贴
- [ ] 考试/练习结束后学生是否能够查看记录?如果能查看的记录需要带答案吗?
- [ ] api角色校验 是否要允许学生用户访问所有api (最后统一处理鉴权)

## 详细功能

### 班级组织管理功能

1. 添加/删除/修改 班级（班级信息）
   - 新增组织时如果该组织存在老师则无法新增
2. 为班级设置老师
   - 如果老师存在则直接替换 旧老师为新老师
   - 如果组织拥有下级部门则无法设置老师

### 题目导入

1. 题目图片上传
   - 图片不会重复上传，如果文件`md5`相同则不会重复上传服务器会直接返回
   - 直接返回的图片如果不存在仍然会重新插入
2. 

### 试卷发布功能

1. 发布指定试卷

### 考试结束后批卷

1. 左侧学生列表 - 点击后显示该学生题号 - 答案 - （正确答案）
2. 右侧提前显示得分 - 可以自行修改得分
3. 批卷内容自动上传更新

## 后端config设置

```yaml
system:
  project-path: "/home/yuri/love-yuri/mathExamMaster/kotlin/src/main/kotlin"
  redis-default-timeout: 21600 # redis内容默认过期时间
  upload-path: null # 文件上传路径
  user-info-timeout: 21600 # 用户信息过期时间默认s
```

