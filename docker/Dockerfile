# ./docker/Dockerfile
# --- Runtime (最终的生产环境镜像) ---
# 使用基于 Alpine 的 JDK 21 JRE 镜像，体积更小，适合内存受限环境
FROM eclipse-temurin:21.0.7_6-jre-alpine

# 设置维护者信息（可选）
LABEL maintainer="2063298305@qq.com"

# 复制 JAR 文件到容器的 /app 目录
# 假设你的 JAR 包已提前在本地打包好，并放在 Dockerfile 同级目录下，名为 micro-forum.jar
# 我们将其复制到容器内部的 /app 目录下，并重命名为 app.jar
COPY micro-forum.jar /app/app.jar

# 创建日志和上传文件的目录，并设置所有者
# 这样做有助于确保容器启动时目录存在，并解决可能的权限问题
RUN mkdir -p /app/logs \
    && mkdir -p /opt/app-data/micro-forum/uploads \
    && addgroup --system springboot && adduser --system --ingroup springboot springboot \
    && chown -R springboot:springboot /app \
    && chown -R springboot:springboot /opt/app-data

# 设置工作目录
WORKDIR /app

# 切换到非 root 用户运行，增强安全性
USER springboot

# 暴露 Spring Boot 监听的端口
EXPOSE 8080

# 启动 Spring Boot 应用
# -Djava.security.egd=file:/dev/./urandom 加速随机数生成
# -Dspring.profiles.active=prod 明确激活生产环境配置
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "app.jar"]