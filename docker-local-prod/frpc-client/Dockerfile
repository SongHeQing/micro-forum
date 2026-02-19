# FRP客户端Dockerfile
FROM alpine:latest

# 安装必要的工具
RUN apk add --no-cache wget tar

# 复制本地的FRP客户端压缩包并安装
COPY frp_0.65.0_linux_amd64.tar.gz /tmp/
RUN tar -zxvf /tmp/frp_0.65.0_linux_amd64.tar.gz -C /tmp/ \
    && cd /tmp/frp_0.65.0_linux_amd64 \
    && cp frpc /usr/local/bin/ \
    && chmod +x /usr/local/bin/frpc \
    && cd / \
    && rm -rf /tmp/frp*

# 创建FRP配置目录
RUN mkdir -p /etc/frp

# 复制配置文件
COPY frpc.yaml /etc/frp/frpc.yaml

# 设置工作目录
WORKDIR /etc/frp

# 启动FRP客户端
CMD ["frpc", "-c", "/etc/frp/frpc.yaml"]