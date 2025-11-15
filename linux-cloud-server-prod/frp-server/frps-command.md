# FRP服务端部署与启动命令

### 1. 创建FRP安装目录
```bash
sudo mkdir -p /opt/frp
```

### 2. 下载FRP服务端
```bash
wget https://github.com/fatedier/frp/releases/download/v0.58.0/frp_0.58.0_linux_amd64.tar.gz
```

### 3. 解压FRP到指定目录
```bash
sudo tar -zxvf frp_0.58.0_linux_amd64.tar.gz -C /opt/frp
```

### 4. 进入FRP目录
```bash
cd /opt/frp/frp_0.58.0_linux_amd64
```

### 5. 配置文件准备
重要：将您配置好的 frps.toml 文件上传到这个目录中，替换默认的配置文件，或者在该目录中创建新的 frps.toml 文件。

### 6. 将FRP环境变量配置添加到系统配置文件中
```bash
sudo echo "# FRP Server Environment Variables" >> /etc/profile
sudo cat ../../profile >> /etc/profile
```

### 7. 重载系统配置文件
```bash
source /etc/profile
```

### 8. 创建FRP日志目录
```bash
sudo mkdir -p /var/log/frp
sudo touch /var/log/frp/frps.log
```

### 9. 查找FRP进程PID（如果正在运行）
```bash
echo "正在查找FRP进程..."
ps aux | grep frps | grep -v grep
```

### 10. 杀死FRP进程（如果正在运行）
```bash
echo "正在杀死FRP进程..."
sudo pkill -f frps
# 或者使用更精确的方式杀死进程：
# FRP_PID=$(ps aux | grep frps | grep -v grep | awk '{print $2}')
# if [ ! -z "$FRP_PID" ]; then
#   sudo kill -9 $FRP_PID
# fi
```

### 11. 以后台模式启动FRP服务端
```bash
nohup ./frps -c ./frps.toml > /var/log/frp/frps.log 2>&1 &

nohup ./frps -c ./frps.toml
```

### 12. 查看FRP日志
```bash
tail -f /var/log/frp/frps.log
```