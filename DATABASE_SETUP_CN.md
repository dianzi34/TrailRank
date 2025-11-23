# 数据库设置说明

## 快速开始

代码现在可以正常运行了！以下是数据库设置方法：

### 1. 数据库位置
- **数据库名称**：`TrailRank`
- **主机**：`localhost:3306`（本地数据库，每个人使用自己的MySQL）
- **用户名**：`root`
- **密码**：**请使用你自己的MySQL root密码**（不是 `PASSWORD123`！）
- **重要提示**：这是本地数据库，每个人需要在自己的电脑上设置，并使用自己的MySQL密码

### 2. 设置步骤

#### 步骤1：创建数据库和表
运行schema脚本来创建所有必需的表：

```bash
mysql -u root -p < src/main/resources/trailrank_schema.sql
```

提示输入密码时，输入你的MySQL root密码。

#### 步骤2：配置你自己的数据库密码
**重要**：每个人必须修改配置文件中的密码为你自己的MySQL密码！

打开 `src/main/resources/application.properties`，找到这一行：
```properties
spring.datasource.password=PASSWORD123
```

把 `PASSWORD123` 改成你自己的MySQL root密码。

完整的数据库配置应该是：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/TrailRank?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=你的MySQL密码
```

#### 步骤3：运行应用程序
```bash
./mvnw spring-boot:run
```

应用程序将在 `http://localhost:8082` 启动

### 3. 数据库表

schema脚本会创建以下表：
- `User` - 用户账户和资料
- `Trail` - 徒步路线信息
- `Collection` - 用户路线收藏
- `Rating` - 路线评分和评论
- `UserRelationship` - 用户关注/取消关注关系

### 4. 重要提示

- **这是本地数据库**：每个人在自己的电脑上运行，使用自己的MySQL，不需要共享密码
- 启动应用程序前确保MySQL正在运行
- **必须修改密码**：每个人都需要在 `application.properties` 中把自己的MySQL密码填进去
- schema脚本包含测试用的示例数据
- 数据库只存在于你的本地电脑，不会影响其他人的数据库

### 5. 故障排除

如果遇到连接错误：
1. 验证MySQL是否运行：`mysql -u root -p`
2. 检查 `TrailRank` 数据库是否存在：`SHOW DATABASES;`
3. 验证表是否存在：`USE TrailRank; SHOW TABLES;`
4. 如果需要，更新 `application.properties` 中的密码

