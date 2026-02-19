# Linux常用文件操作命令

## 1. 查看文件和目录

### ls - 列出目录内容
- `ls` - 显示当前目录内容
- `ls -l` - 显示详细信息（权限、所有者、大小、修改时间等）
- `ls -la` - 显示所有文件（包含隐藏文件）
- `ls -lh` - 以人类可读的方式显示文件大小

### pwd - 显示当前工作目录的绝对路径
```bash
pwd
```

## 2. 查看文件内容

### cat - 显示整个文件内容
```bash
cat 文件名
```

### less - 分页查看文件内容
```bash
less 文件名  # 按q退出
```

### more - 分页查看文件内容
```bash
more 文件名
```

### head - 显示文件开头几行
```bash
head 文件名        # 默认显示前10行
head -n 20 文件名  # 显示前20行
```

### tail - 显示文件末尾几行
```bash
tail 文件名        # 默认显示后10行
tail -n 20 文件名  # 显示最后20行
tail -f 文件名     # 实时查看文件新增内容（常用于查看日志）
```

## 3. 文本搜索与编辑

### grep - 在文件中搜索内容
```bash
grep "关键词" 文件名                         # 在文件中搜索指定内容
grep -r "关键词" /path                       # 递归搜索目录中的内容
grep -i "关键词" 文件名                      # 忽略大小写搜索
grep -n "关键词" 文件名                      # 显示匹配行的行号
```

### sed - 流编辑器（用于文本替换和处理）
```bash
sed 's/old_text/new_text/' 文件名             # 替换第一次出现的文本
sed 's/old_text/new_text/g' 文件名            # 替换所有出现的文本
sed -i 's/old_text/new_text/g' 文件名         # 直接修改原文件
sed '2d' 文件名                              # 删除第2行
sed '2a\新行内容' 文件名                      # 在第2行后添加新行
```

### awk - 文本处理语言
```bash
awk '{print $1}' 文件名                       # 打印第一列
awk '/pattern/{print}' 文件名                  # 打印匹配pattern的行
awk -F',' '{print $2}' 文件名                 # 使用逗号作为分隔符，打印第二列
awk 'NR==3{print}' 文件名                     # 打印第三行
```

## 4. 文本编辑器

### vi/vim - 强大的文本编辑器
```bash
vim 文件名                                  # 打开文件进行编辑

# Vim 三种模式：
# 1. 命令模式 - 移动光标和执行命令
# 2. 插入模式 - 输入文本
# 3. 底线命令模式 - 保存退出等操作

# 常用快捷键：
i                                         # 在光标前插入
a                                         # 在光标后插入
o                                         # 在当前行下方新开一行并插入
h j k l                                   # 左下上右移动光标
dd                                        # 删除当前行
dw                                        # 删除当前单词
:x                                        # 保存并退出
:q!                                       # 强制退出不保存
:wq                                       # 保存并退出
/关键词                                     # 搜索关键词
n                                         # 下一个匹配项
N                                         # 上一个匹配项
```

### nano - 简单易用的文本编辑器
```bash
nano 文件名                                # 打开nano编辑器
# Ctrl+O: 保存文件
# Ctrl+X: 退出
# Ctrl+W: 搜索文本
```

## 5. 搜索和查找

### find - 按条件查找文件
```bash
find /path -name "文件名"                    # 按名称查找文件
find /path -type f -name "*.txt"             # 查找特定类型的文件
```

## 6. 文本处理命令

### wc - 统计命令
```bash
wc 文件名                                 # 统计行数、词数、字符数
wc -l 文件名                              # 统计行数
wc -w 文件名                              # 统计词数
wc -c 文件名                              # 统计字符数
```

### sort - 排序命令
```bash
sort 文件名                               # 按字母顺序排序
sort -n 文件名                            # 按数字顺序排序
sort -r 文件名                            # 反向排序
sort -k 2 文件名                          # 按第二列排序
```

### uniq - 去重命令
```bash
uniq 文件名                               # 删除相邻重复行
sort 文件名 | uniq                        # 删除所有重复行
uniq -c 文件名                            # 统计重复次数
```

### cut - 截取命令
```bash
cut -d',' -f2 文件名                       # 按逗号分割，提取第2列
cut -c1-10 文件名                         # 提取第1到第10个字符
cut -f1,3 文件名                          # 提取第1和第3列
```

### tr - 转换或删除字符
```bash
echo "hello" | tr 'a-z' 'A-Z'              # 转换为大写
tr -d 's' < 文件名                        # 删除所有's'字符
tr '\n' ' ' < 文件名                      # 将换行符替换为空格
```

## 7. 文件比较

### diff - 比较文件差异
```bash
diff 文件1 文件2                           # 比较两个文件的不同
diff -u 文件1 文件2                        # 以统一格式显示差异
```

### comm - 比较两个已排序文件
```bash
comm 文件1 文件2                           # 比较两个已排序文件的差异
comm -12 文件1 文件2                       # 显示两个文件共有的行
```

## 8. 文件属性和权限

### stat - 显示文件详细状态信息
```bash
stat 文件名
```

### du - 显示目录空间使用情况
```bash
du -h                                      # 显示目录空间使用情况
du -sh /path                               # 显示目录总大小
```

### file - 检测文件类型
```bash
file 文件名
```

## 9. 目录操作

### cd - 切换目录
```bash
cd /path                                   # 切换到指定目录
```

### mkdir - 创建目录
```bash
mkdir 目录名                                # 创建单个目录
mkdir -p /path/to/dir                      # 创建多级目录
```

### tree - 以树形结构显示目录
```bash
tree                                       # 需要先安装tree包
```

## 10. 文件操作

### touch - 创建空文件或更新时间戳
```bash
touch 文件名                                # 创建空文件或更新时间戳
```

### cp - 复制文件
```bash
cp 源文件 目标文件                          # 复制文件
```

### mv - 移动或重命名文件
```bash
mv 旧文件名 新文件名                        # 重命名或移动文件
```

### rm - 删除文件或目录
```bash
rm 文件名                                  # 删除文件
rm -rf 目录名                              # 强制删除目录及内容
```

## 11. 权限管理

### chmod - 修改文件权限
```bash
chmod 755 文件名                            # 修改文件权限
```

### chown - 修改文件所有者
```bash
chown 用户名 文件名                         # 修改文件所有者
```

### chgrp - 修改文件所属组
```bash
chgrp 组名 文件名                           # 修改文件所属组
```

## 12. 检查运行中的服务和程序

### ps - 显示当前运行的进程
```bash
ps aux                                    # 显示所有运行的进程详细信息
ps aux | grep mysql                       # 查找MySQL进程
ps aux | grep nginx                       # 查找Nginx进程
ps aux | grep java                        # 查找Java进程
```

### top - 实时显示系统中各个进程的资源占用状况
```bash
top                                       # 显示实时进程信息
# 按 q 退出
```

### systemctl - 管理系统服务
```bash
systemctl status mysql                    # 检查MySQL服务状态
systemctl status nginx                    # 检查Nginx服务状态
systemctl status docker                   # 检查Docker服务状态
systemctl list-units --type=service       # 列出所有服务的状态
```

### ss - 显示套接字统计信息（现代系统中netstat的替代品）
```bash
ss -tuln                                  # 显示所有监听的TCP和UDP端口（相当于netstat -tuln）
ss -tuln | grep :3306                    # 检查MySQL端口(3306)是否在监听
ss -tuln | grep :80                      # 检查HTTP端口(80)是否在监听
ss -tuln | grep :443                     # 检查HTTPS端口(443)是否在监听
```

### netstat - 显示网络连接和监听端口（如果系统已安装）
```bash
# 注意：在许多现代Linux发行版中，netstat已被ss命令替代
# 如果您的系统没有netstat，可能需要安装net-tools包
netstat -tuln                             # 显示所有监听的TCP和UDP端口
netstat -tuln | grep :3306               # 检查MySQL端口(3306)是否在监听
netstat -tuln | grep :80                  # 检查HTTP端口(80)是否在监听
netstat -tuln | grep :443                 # 检查HTTPS端口(443)是否在监听
```

### 检查特定服务是否运行

#### 检查 MySQL 是否运行
```bash
# 方法1：使用 systemctl 检查
systemctl status mysql
# 或者
systemctl status mysqld

# 方法2：使用 ps 命令检查进程
ps aux | grep mysql

# 方法3：使用 ss 检查端口
ss -tuln | grep :3306

# 方法4：尝试连接 MySQL
mysql -u root -p                          # 尝试连接MySQL
```

#### 检查 Docker 是否运行
```bash
# 检查 Docker 服务状态
systemctl status docker

# 检查 Docker 是否响应命令
docker info

# 查看正在运行的容器
docker ps

# 查看所有容器（包括停止的）
docker ps -a
```

#### 检查 Nginx 是否运行
```bash
# 检查 Nginx 服务状态
systemctl status nginx

# 检查 Nginx 进程
ps aux | grep nginx

# 检查 80 和 443 端口是否被 Nginx 占用
ss -tuln | grep :80
ss -tuln | grep :443
```

## 13. 磁盘和挂载

### df - 显示磁盘使用情况
```bash
df -h                                      # 显示磁盘使用情况
```

### mount 和 lsblk
```bash
mount                                      # 显示已挂载设备
lsblk                                      # 显示块设备信息
```

## 14. 环境变量管理

### 查看环境变量

```bash
# 查看所有环境变量
env

# 查看特定环境变量
echo $VAR_NAME
printenv VAR_NAME

# 查看所有环境变量和shell函数
set

# 查看所有环境变量和导出的变量
export -p
```

### 设置环境变量

```bash
# 临时设置（仅当前会话有效）
export VAR_NAME=value

# 临时设置（仅当前命令有效）
VAR_NAME=value command

# 设置变量但不导出（仅当前shell有效，子进程无法访问）
VAR_NAME=value
```

# 方式1：仅对一个命令有效
BIND_PORT=7000 frps -c frps.toml

# 方式2：设置后，后续命令可使用（但子进程无法访问）
BIND_PORT=7000
echo $BIND_PORT  # 输出: 7000
frps -c frps.toml  # 这里可能无法访问到 BIND_PORT

# 方式3：导出到环境（推荐用于服务）
export BIND_PORT=7000
echo $BIND_PORT  # 输出: 7000
frps -c frps.toml  # 这里可以访问到 BIND_PORT


### 永久设置环境变量（文本操作方式）

在Linux系统中，环境变量可以通过编辑配置文件永久设置。以下是几种常见的配置文件及其编辑方法：

#### 1. 用户级别的环境变量

##### 编辑 ~/.bashrc 文件（仅对当前用户生效）

```bash
# 使用文本编辑器打开 ~/.bashrc 文件
nano ~/.bashrc
# 或者
vim ~/.bashrc

# 在文件末尾添加环境变量
echo 'export VAR_NAME=value' >> ~/.bashrc

# 重新加载配置文件
source ~/.bashrc
```

##### 编辑 ~/.bash_profile 文件

```bash
# 使用文本编辑器打开 ~/.bash_profile 文件
nano ~/.bash_profile

# 在文件中添加环境变量
echo 'export VAR_NAME=value' >> ~/.bash_profile

# 重新加载配置文件
source ~/.bash_profile
```

##### 编辑 ~/.profile 文件

```bash
# 使用文本编辑器打开 ~/.profile 文件
nano ~/.profile

# 在文件中添加环境变量
echo 'export VAR_NAME=value' >> ~/.profile

# 重新加载配置文件
source ~/.profile
```

#### 2. 系统级别的环境变量

##### 编辑 /etc/environment 文件（影响所有用户，系统级设置）

`/etc/environment` 文件用于设置系统范围的环境变量，它在系统启动或用户登录的早期阶段就会被读取。

格式特点：
- 使用 `NAME=VALUE` 格式
- **不支持变量扩展**（不能使用 `$OTHER_VAR` 或 `$HOME`）
- **不需要引号**包围值（即使有特殊字符）
- **不需要**使用 `export` 命令
- 每行一个变量定义

```bash
# 使用管理员权限编辑 /etc/environment 文件
sudo nano /etc/environment

# 正确的格式示例：
JAVA_HOME=/usr/lib/jvm/default-java
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
MY_APP_CONFIG=/opt/myapp/config
```

**重要提示**：在 `/etc/environment` 中，你不能使用：
- 变量扩展（如 `$PATH` 或 `$HOME`）
- `export` 命令
- 引号包围值

修改后需要重新登录或重启系统才能生效。

##### 编辑 /etc/profile 文件（影响所有用户，支持shell脚本）

`/etc/profile` 是一个 shell 脚本文件，在用户登录时被执行。

格式特点：
- 使用标准 shell 语法
- 需要使用 `export` 命令声明环境变量
- 支持变量扩展和复杂的 shell 语法

```bash
# 使用管理员权限编辑 /etc/profile 文件
sudo nano /etc/profile

# 在文件末尾添加环境变量（使用标准shell语法）
echo 'export VAR_NAME=value' | sudo tee -a /etc/profile

# 也可以使用变量扩展
echo 'export PATH=$PATH:/new/path' | sudo tee -a /etc/profile

# 重新加载配置文件
source /etc/profile
```

##### /etc/environment 与 /etc/profile 的区别

| 特性 | /etc/environment | /etc/profile |
|------|------------------|---------------|
| 格式 | `NAME=VALUE` | `export NAME=VALUE` |
| 引号 | 不使用 | 可以使用 |
| 变量扩展 | 不支持 (`$VAR`) | 支持 (`$VAR`) |
| export 命令 | 不需要 | 需要 |
| 读取时机 | 登录早期，由 PAM 读取 | 用户登录时由 shell 执行 |
| 适用性 | 适用于所有 shell 类型 | 依赖于 shell 类型 |
| 立即生效 | 通常需要重新登录 | 可通过 `source` 立即生效 |
| 用途 | 设置静态、全局的基础变量 | 适用于需要动态计算或条件判断的配置 |

使用场景：
- **`/etc/environment`**：适合设置静态的全局变量，如基本的 `PATH` 或应用路径
- **`/etc/profile`**：适合需要动态计算的环境变量或更复杂的初始化脚本

#### 3. 使用 sed 命令编辑环境变量配置文件

```bash
# 在文件末尾添加环境变量
sed -i '$a export VAR_NAME=value' ~/.bashrc

# 替换特定行（例如修改已存在的变量）
sed -i 's/^export OLD_VAR=.*/export OLD_VAR=new_value/' ~/.bashrc

# 在特定模式后插入行
sed -i '/# Environment/a export NEW_VAR=value' ~/.bashrc
```

#### 4. 使用 grep 和 awk 进行环境变量管理

```bash
# 检查环境变量是否已存在
grep -q "^export VAR_NAME=" ~/.bashrc && echo "变量已存在" || echo "变量不存在"

# 查找特定环境变量
grep "^export VAR_NAME=" ~/.bashrc

# 获取环境变量的值
VAR_VALUE=$(grep "^export VAR_NAME=" ~/.bashrc | cut -d'=' -f2)
echo "变量值为: $VAR_VALUE"
```

#### 5. 删除环境变量

```bash
# 从配置文件中删除特定环境变量（使用sed）
sed -i '/^export VAR_NAME=/d' ~/.bashrc

# 删除临时环境变量
unset VAR_NAME
```

#### 6. 备份和恢复配置文件

```bash
# 在修改前备份配置文件
cp ~/.bashrc ~/.bashrc.backup.$(date +%Y%m%d_%H%M%S)

# 恢复配置文件
cp ~/.bashrc.backup.timestamp ~/.bashrc

# 比较修改前后的差异
diff ~/.bashrc ~/.bashrc.backup.timestamp
```

#### 7. 验证配置文件语法

```bash
# 检查 bash 配置文件语法是否正确
bash -n ~/.bashrc

# 加载配置文件并检查是否有错误
source ~/.bashrc
```

### Docker环境变量管理

```bash
# Docker Compose中使用环境变量
docker-compose up -e VAR_NAME=value

# 在容器中设置环境变量
docker run -e VAR_NAME=value image_name

# Docker Compose会自动从 .env 文件加载变量
docker-compose up
```

### 常见的系统环境变量

```bash
# PATH - 可执行文件路径
echo $PATH

# HOME - 用户主目录
echo $HOME

# USER - 当前用户名
echo $USER

# LANG - 系统语言
echo $LANG

# JAVA_OPTS - Java虚拟机选项
echo $JAVA_OPTS

# TZ - 时区设置
echo $TZ
```

### 实际应用场景示例

在micro-forum项目中，环境变量被广泛用于配置不同的服务：

- `SPRING_PROFILES_ACTIVE`: 指定Spring Boot使用的配置文件
- `MYSQL_ROOT_PASSWORD`: MySQL数据库密码
- `REDIS_PASSWORD`: Redis密码
- `JWT_SECRET_KEY`: JWT加密密钥
- `EMAIL_*`: 邮件服务配置
- `MINIO_*`: MinIO存储服务配置

这些环境变量通常在Docker Compose文件中引用`${VARIABLE_NAME}`格式的变量，实际值从配置文件或`.env`文件中加载。

## 15. 实际应用示例

### 文本处理示例
```bash
# 搜索日志文件中的错误并统计数量
grep "ERROR" /var/log/app.log | wc -l

# 提取访问日志中的IP地址并统计访问次数
cat access.log | cut -d' ' -f1 | sort | uniq -c | sort -nr

# 查找特定模式并替换后保存到新文件
sed 's/old_value/new_value/g' input.txt > output.txt

# 显示文件中长度超过80个字符的行
awk 'length($0) > 80' 文件名
```

这些命令可以帮助您更好地管理和处理Linux系统上的文本内容，以及检查系统中运行的服务和程序。