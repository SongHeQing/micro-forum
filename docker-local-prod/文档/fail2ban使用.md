Fail2ban 是一个非常实用的 Linux 安全工具，它通过**监控日志文件**（如 `/var/log/auth.log`、`/var/log/nginx/access.log` 等），自动检测恶意行为（如多次登录失败），并**临时或永久封禁对应的 IP 地址**（通常通过 iptables/nftables/ufw 实现）。

下面以 **最常见的 SSH 防暴力破解** 为例，手把手教你安装、配置和使用 Fail2ban。

---

## ✅ 一、安装 Fail2ban

### Ubuntu/Debian：
```bash
sudo apt update
sudo apt install fail2ban
```

### CentOS/RHEL（需启用 EPEL）：
```bash
sudo yum install epel-release
sudo yum install fail2ban
# 或在 CentOS 8+/RHEL 8+ 使用 dnf
sudo dnf install fail2ban
```

安装完成后，服务会自动启动：
```bash
sudo systemctl enable --now fail2ban
```

---

## ✅ 二、基本配置（推荐“不改主配置”原则）

Fail2ban 的配置目录是 `/etc/fail2ban/`。  
**最佳实践：不要直接修改 `jail.conf`，而是创建 `jail.local` 覆盖默认设置。**

```bash
sudo cp /etc/fail2ban/jail.conf /etc/fail2ban/jail.local
```

> 📌 所有自定义配置都写在 `jail.local` 中，这样升级时不会被覆盖。

---

## ✅ 三、启用 SSH 防护（最常用）

编辑 `/etc/fail2ban/jail.local`：

```ini
[sshd]
enabled = true
port = ssh
logpath = %(sshd_log)s
maxretry = 3          # 允许失败次数
bantime = 1h          # 封禁时长（1小时）
findtime = 10m        # 在10分钟内超过 maxretry 次就封
```

> 🔍 默认 `%(sshd_log)s` 会自动指向正确的日志路径（如 Ubuntu 是 `/var/log/auth.log`，CentOS 是 `/var/log/secure`）。

---

## ✅ 四、（可选）自定义全局参数

在 `[DEFAULT]` 段中设置通用规则（在 `jail.local` 顶部）：

```ini
[DEFAULT]
# 封禁方式：iptables, nftables, ufw 等（根据系统自动选择）
banaction = iptables-multiport

# 发送邮件告警（需配置 MTA，如 postfix/sendmail）
# action = %(action_mwl)s

# 默认封禁时长
bantime = 1h

# 默认查找时间窗口
findtime = 10m

# 默认最大重试次数
maxretry = 5
```

---

## ✅ 五、重启 Fail2ban 生效

```bash
sudo systemctl restart fail2ban
```

---

## ✅ 六、查看状态 & 测试

### 查看所有 jail 状态：
```bash
sudo fail2ban-client status
```

### 查看 SSH jail 的封禁列表：
```bash
sudo fail2ban-client status sshd
```

输出示例：
```
Status for the jail: sshd
|- Filter
|  |- Currently failed: 0
|  |- Total failed:     12
|  `- Journal matches:  _SYSTEMD_UNIT=sshd.service + _COMM=sshd
`- Actions
   |- Currently banned: 1
   |- Total banned:     3
   `- Banned IP list:   203.0.113.50
```

### 手动测试（⚠️ 别用自己的 IP！）：
从另一台机器故意输错 SSH 密码 3 次，观察是否被封。

---

## ✅ 七、解封 IP（如果误封）

```bash
# 解封某个 IP（例如 203.0.113.50）
sudo fail2ban-client set sshd unbanip 203.0.113.50

# 或直接重启（不推荐，会清空所有封禁）
sudo systemctl restart fail2ban
```

---

## ✅ 八、扩展：保护 Web 服务（如 Nginx/Apache）

### 示例：防 WordPress 登录爆破（针对 `/wp-login.php`）

1. 创建 filter 文件：`/etc/fail2ban/filter.d/nginx-wplogin.conf`

```ini
[Definition]
failregex = ^<HOST>.*POST.*/wp-login\.php.*HTTP/.*$
ignoreregex =
```

2. 在 `jail.local` 中启用：

```ini
[nginx-wplogin]
enabled = true
port = http,https
filter = nginx-wplogin
logpath = /var/log/nginx/access.log
maxretry = 5
bantime = 2h
findtime = 10m
```

3. 重启 Fail2ban。

> 💡 类似方法可用于防 Nginx/Apache 的 404 扫描、HTTP Basic Auth 爆破等。

---

## ✅ 九、安全建议

1. **不要只依赖 Fail2ban**：
   - 禁用 root 登录：`PermitRootLogin no`
   - 改 SSH 端口（可选）：`Port 2222`
   - **强制使用 SSH 密钥认证**（比密码安全得多）

2. **Fail2ban 日志位置**：
   - 主日志：`/var/log/fail2ban.log`
   - 可用于排查为何没触发封禁

3. **性能影响极小**：Fail2ban 只读日志，不监听网络，资源占用低。

---

## 总结：3 步搞定基础防护

```bash
1. sudo apt install fail2ban
2. sudo nano /etc/fail2ban/jail.local → 启用 [sshd]
3. sudo systemctl restart fail2ban
```

✅ 完成！你的服务器现在能自动封禁暴力破解 SSH 的 IP 了。

需要我帮你写一个完整的 `jail.local` 示例，或者针对 Docker、Postfix、ProFTPD 等服务的防护规则吗？