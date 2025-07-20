@echo off
echo 启动 Micro Forum 应用...
java -Dfile.encoding=UTF-8 -Dspring.profiles.active=dev -jar target/micro-forum-0.0.1-SNAPSHOT.jar
pause 