# 团队通知 - 代码已可正常运行！

大家好，

好消息！代码现在已经可以正常运行了。以下是需要了解的信息：

## 数据库设置

### 快速设置（3个步骤）：

1. **创建数据库和表：**
   ```bash
   mysql -u root -p < src/main/resources/trailrank_schema.sql
   ```
   提示输入密码时，输入你的MySQL root密码。

2. **数据库配置：**
   - 数据库名称：`TrailRank`
   - 用户名：`root`
   - 密码：**请使用你自己的MySQL root密码**（不是 `PASSWORD123`！）
   - 重要：每个人需要修改 `src/main/resources/application.properties` 文件中的密码为你自己的MySQL密码
   - 找到这一行：`spring.datasource.password=PASSWORD123`，把 `PASSWORD123` 改成你自己的MySQL密码

3. **运行应用程序：**
   ```bash
   ./mvnw spring-boot:run
   ```
   然后在浏览器中打开 `http://localhost:8082`

## 已实现的功能

- 用户认证（登录、注册、登出）
- 路线浏览和搜索
- 收藏功能（添加/删除路线）
- 用户资料
- 评分和评论
- 关注/取消关注功能
- 所有页面都能正常显示

## 数据库位置

数据库配置文件位置：
- **配置文件**：`src/main/resources/application.properties`
- **数据库脚本**：`src/main/resources/trailrank_schema.sql`

数据库将创建在你本地的MySQL服务器上，地址为 `localhost:3306`。

## 需要帮助？

如果遇到问题：
1. 确保MySQL正在运行
2. 检查 `TrailRank` 数据库是否存在
3. **重要**：确认 `application.properties` 中的密码是你自己的MySQL密码（不是别人的密码！）

如有任何问题，请告诉我！

