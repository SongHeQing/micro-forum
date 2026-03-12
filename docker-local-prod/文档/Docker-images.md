
## 项目中使用的镜像

### 数据库相关
- **MySQL**: `mysql:8.0`
  - 用于存储应用数据
  - 初始化脚本位于 `docker/mysql/init/*.sql`

- **Redis**: `redis:latest`
  - 用于缓存热点数据
  - 配置连接池使用 commons-pool2

### 文件存储
- **MinIO**: `minio/minio:latest`
  - 用于文件上传与管理
  - 替代传统的文件系统存储

### 基础设施
- **Nginx**: `nginx:latest`
  - 作为反向代理服务器
  - 静态资源托管

## 常用开发镜像

### Java 环境
- **OpenJDK**: `openjdk:21-jdk`
  - Java 21 开发环境
  - 适用于 Spring Boot 3.4.7+

- **Tomcat**: `tomcat:10.1-jdk21`
  - Java Web 应用容器

### 数据库
- **PostgreSQL**: `postgres:15`
  - 关系型数据库选择之一

- **MongoDB**: `mongo:7`
  - 文档型数据库

- **Elasticsearch**: `elasticsearch:8.10.0`
  - 搜索引擎服务

### 消息队列
- **RabbitMQ**: `rabbitmq:3-management`
  - 消息队列服务

- **Apache Kafka**: `confluentinc/cp-kafka:latest`
  - 分布式流处理平台

### 监控工具
- **Prometheus**: `prom/prometheus`
  - 系统监控和报警工具

- **Grafana**: `grafana/grafana`
  - 数据可视化和仪表盘工具

- **Jaeger**: `jaegertracing/all-in-one:latest`
  - 分布式追踪系统

## Docker 加速配置

根据你提供的配置，以下是 `/etc/docker/daemon.json` 的内容：

```json
{
    "registry-mirrors": [
        "https://docker.1ms.run",
        "https://docker.xuanyuan.me"
    ]
}
```

要使配置生效，请重启 Docker 服务：

```bash
sudo systemctl restart docker
```

> 注意：与大多数 systemd 服务不同，修改 Docker 的 daemon.json 配置文件后，不需要运行 `systemctl daemon-reload` 命令。
> Docker 服务会在重启时自动加载新的配置文件。`daemon-reload` 主要用于重新加载 systemd 单元文件（即 /etc/systemd/system/ 目录下的服务定义文件），
> 而不是用于 Docker 的 daemon.json 配置文件。

## 常用 Docker 命令

### 镜像操作
- 列出本地镜像: `docker images`
- 拉取镜像: `docker pull <镜像名>`
- 删除镜像: `docker rmi <镜像ID>`
- 查看镜像历史: `docker history <镜像名>`

### 容器操作
- 运行容器: `docker run -d -p <宿主机端口>:<容器端口> <镜像名>`
- 查看运行中的容器: `docker ps`
- 查看所有容器: `docker ps -a`
- 停止容器: `docker stop <容器ID>`
- 启动容器: `docker start <容器ID>`
- 进入容器: `docker exec -it <容器ID> bash`

### 日志查看
- 查看容器日志: `docker logs <容器ID>`
- 实时查看日志: `docker logs -f <容器ID>`

## Docker Compose 示例

创建 `docker-compose.yml` 文件来管理多个服务：

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
    environment:
      - SPRING_PROFILES_ACTIVE=docker

  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: micro_forum
    volumes:
      - ./docker/mysql/init:/docker-entrypoint-initdb.d

  redis:
    image: redis:latest
    ports:
      - "6379:6379"

  minio:
    image: minio/minio:latest
    ports:
      - "9000:9000"
      - "9001:9001"
    environment:
      MINIO_ROOT_USER: admin
      MINIO_ROOT_PASSWORD: minioadmin
    volumes:
      - minio_data:/data
    command: server /data --console-address ":9001"

volumes:
  minio_data:
```

## 最佳实践

1. **镜像大小优化**
   - 使用 Alpine 版本的基础镜像来减小体积
   - 合理使用 `.dockerignore` 文件排除不必要的文件

2. **安全考虑**
   - 定期更新基础镜像
   - 使用非 root 用户运行容器
   - 不要在镜像中存储敏感信息

3. **性能优化**
   - 使用多阶段构建减少最终镜像大小
   - 合理配置容器资源限制

4. **监控与日志**
   - 配置集中式日志收集
   - 设置健康检查机制