这是极客时间专栏《即时消息技术剖析与实战》代码改造的后端项目。
这个聊天室的大概功能有：
1. 支持用户的登录。
2. 双方支持简单的文本聊天。
3. 支持消息未读数（包括总未读和会话未读）。
4. 支持联系人页和未读数有新消息的自动更新。
5. 支持聊天页有新消息时自动更新。


用户名  | 用户邮箱 | 用户密码
--------- | -------- | -------
张三 | zhangsan@gmail.com | 1234
李四 | lisi@gmail.com | 1234
王五 | wangwu@hotmail.com | 1234


### 框架
长连接的实现上采用了netty作为nio的框架，结合spring boot以及redis的“发布/订阅”功能来完成。
