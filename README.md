# springboot-mybatis

因为项目需要，做了一个 Springboot, Mybatis(Plus) 的项目模版。在基础框架之上，从小鹰软件的 Spring MVC/JPA 框架中简单移植了如下内容：
由于我并不欣赏 Mybatis 以及 Plus 的理念和实现，因此不打算继续移植或优化下去。

- 增删查改框架
- Token 授权和验证的框架与实现（好用，支持灵活扩展，不用每个项目自己写）
- 前后端共用的验证支持（好用，统一的验证）
- 接口返回 JSON 时的关联对象注入（好用，如 User.organizationId, 返回接口是将自动注入一个 Organization 对象）
- 应用接口或服务接口的鉴权（好用，基于标注的鉴权）
- 统一的接口异常返回

各个模块可以独立使用：
- /auth
Token 授权和验证
- /profile
基础的用户管理项目，未完成接口部分
- /framework
框架源代码
- /projectname
项目例子



