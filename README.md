If you have troubles in reading Chinese, please ask AI to translate for you.

# 简介

这是一个简单的图书馆系统，用来展示我在实习期间的学习成果。

这篇README将作为一个项目的概要和总结，以防我忘记一些技术上的细节，同时将作为一个待办事项存在。

先介绍一个项目的基本结构，这是项目的主题逻辑，不会随着版本迭代而改变（大概）：

- 存在“用户（User）”、“图书（Book）”和“记录（Record）”三个实体。
- “用户”可以借“图书”，在这个过程中会产生“记录”。一个“用户”可以借很多本“图书”，但同一本“图书”一次只能被一个“用户”借。
- “用户”对“记录”和“图书”对“记录”都是一对多的关系。
- “用户”和“图书”都可以手动添加，“记录”是在“借”这个过程中自动产生的。

# 1.0版本

前端使用Swagger展示。

使用Spring JPA实现数据库查询。

使用AOP记录日志。

## 实现功能

- 接口文档展示（Swagger）
- 增删查改：
    * 用户：
        + 添加单个用户
        + 浏览所有用户
        + 根据名字查找用户
    * 图书：
        + 添加单本图书
        + 浏览所有图书
        + 根据标题查找图书
    * 记录：
        + 生成借阅记录
        + 还书时将相应借阅记录改为还书
        + 浏览所有记录
        + 查询指定用户的所有记录
        + 查询指定图书的所有记录
- 日志：在Service层中插入日志切面记录相关日志。

## 数据库结构

### users

| 列名   | 备注 |
|------|----|
| id   | 自增 |
| name |    |

### books

| 列名    | 备注       |
|-------|----------|
| id    | 自增       |
| title |          |
| lent  | 为真时表示被借出 |

### records

| 列名          | 备注           |
|-------------|--------------|
| id          | 自增           |
| user_id     | 外键：users中的id |
| book_id     | 外键：books中的id |
| borrow_time | 前端提交，下同      |
| return_time | 可空，为空时表示还未还书 |

## 主要代码结构

这里只展示 *library/src/main/java* 目录下的文件。

```text
com.demo.library
    |   LibraryApplication.java
    |   SwaggerConfig.java
    |
    +---aspect
    |       LogAspect.java
    |
    +---controller
    |       BookController.java
    |       RecordController.java
    |       UserController.java
    |
    +---domain
    |       Book.java
    |       BookRepository.java
    |       Record.java
    |       RecordRepository.java
    |       User.java
    |       UserRepository.java
    |
    \---service
        |   BookService.java
        |   RecordService.java
        |   UserService.java
        |
        \---impl
                BookServiceImpl.java
                RecordServiceImpl.java
                UserServiceImpl.java
                
```

# 2.0版本

将操作数据库从Spring JPA修改为Mybatis。

引入Spring Security，在进行一些操作时需要鉴权。

增加异常处理切面，使得Controller在处理过程中发生异常时能够返回请示失败响应（如“图书不存在”）。

单元测试。

## 实现功能

- 接口文档展示（Swagger）
- 增删查改：
    * 用户：
        + ~~添加单个用户~~ **用户注册**
        + **用户登录**
        + **管理员**浏览所有用户
        + **管理员**根据名字查找用户
    * 图书：
        + **管理员**添加单本图书
        + 浏览所有图书
        + 根据标题查找图书
    * 记录：
        + ~~生成借阅记录~~
        + ~~还书时将相应借阅记录改为还书~~
        + **生成借书、还书、续借、挂失等不同类别的记录**
        + **管理员**浏览所有记录
        + ~~查询指定用户的所有记录~~  
          **管理员可查询任意用户记录，普通用户只能查询自己的记录**
        + ~~查询指定图书的所有记录~~  
          **管理员能查看和指定图书相关的全部记录，普通用户查询和自己相关的记录**
- 日志：在Service层中插入日志切面记录相关日志。

## 数据库结构

### users

| 列名       | 备注 |
|----------|----|
| id       | 自增 |
| name     |    |
| password | 明文 |

### books

| 列名    | 备注                                                          |
|-------|-------------------------------------------------------------|
| id    | 自增                                                          |
| title |                                                             |
| state | 在库In the Library<br/>借出Lend Out<br/>遗失Not Found<br/>报废SCrap |

### recos

| 列名      | 备注           |
|---------|--------------|
| id      | 自增           |
| user_id | 外键：users中的id |
| book_id | 外键：books中的id |

### cords

| 列名      | 备注                                                                      |
|---------|-------------------------------------------------------------------------|
| id      | 自增                                                                      |
| reco_id | 外键：recos中的id                                                            |
| type    | 借书Borrow Book<br/>还书Return Book<br/>续借Renew Order<br/>挂失Report the Loss |
| time    | 后端Service层调用一个Mapper获取                                                  |

## 代码结构

```text
├───.idea
│   ├───codeStyles
│   ├───dataSources
│   │   └───4fb4a1f6-c6d9-4c1f-9cb1-672c85c25fab
│   │       └───storage_v2
│   │           └───_src_
│   │               └───database
│   │                   ├───library.uyToCQ
│   │                   │   └───schema
│   │                   └───postgres.edMnLQ
│   │                       └───schema
│   └───inspectionProfiles
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───demo
│   │   │           └───library
│   │   │               ├───aspect
│   │   │               │   └───exception
│   │   │               │       ├───conroller
│   │   │               │       └───service
│   │   │               ├───config
│   │   │               ├───controller
│   │   │               ├───entity
│   │   │               ├───mapper
│   │   │               ├───service
│   │   │               │   └───impl
│   │   │               └───utils
│   │   └───resources
│   │       └───mapper
│   └───test
│       └───java
│           └───com
│               └───demo
│                   └───library
│                       ├───mapper
│                       └───service
└───target
    ├───classes
    │   ├───com
    │   │   └───demo
    │   │       └───library
    │   │           ├───aspect
    │   │           │   └───exception
    │   │           │       └───service
    │   │           ├───config
    │   │           ├───controller
    │   │           ├───entity
    │   │           ├───mapper
    │   │           ├───service
    │   │           │   └───impl
    │   │           └───utils
    │   └───mapper
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    └───test-classes
        └───com
            └───demo
                └───library
                    ├───mapper
                    └───service
```


经过简单测试上述功能已基本实现，但因要离职了所以没来得及添加针对Controller的单元测试，以后也不打算写Java了所有这个项目也不会再更新了。

——2024.03.14