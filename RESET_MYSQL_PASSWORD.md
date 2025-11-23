# MySQL 8.x 密码重置指南（macOS）

## 方法1：重置密码（推荐）

### 步骤1：停止 MySQL 服务
```bash
# 如果使用 Homebrew 安装：
brew services stop mysql

# 或者使用系统服务：
sudo /usr/local/mysql/support-files/mysql.server stop
```

### 步骤2：以安全模式启动 MySQL
```bash
sudo mysqld_safe --skip-grant-tables --skip-networking &
```

### 步骤3：连接到 MySQL（无需密码）
```bash
mysql -u root
```

### 步骤4：在 MySQL 中重置密码（MySQL 8.x 语法）
```sql
USE mysql;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
EXIT;
```

### 步骤5：重启 MySQL 服务
```bash
# 先杀掉安全模式的进程
sudo pkill mysqld

# 然后正常启动
brew services start mysql
# 或者
sudo /usr/local/mysql/support-files/mysql.server start
```

## 方法2：如果你记得密码

如果你知道你的 MySQL root 密码，直接告诉我，我会帮你更新 `application.properties` 文件。

## 方法3：检查 Keychain（macOS）

macOS 可能将 MySQL 密码存储在 Keychain 中：
1. 打开"钥匙串访问"（Keychain Access）
2. 搜索 "mysql" 或 "root"
3. 查看是否有存储的密码

## 方法4：使用 MySQL Workbench

如果你安装了 MySQL Workbench，可以在那里查看或重置密码。
