@echo off
chcp 65001
echo 启动 Micro Forum 应用（UTF-8编码）...
@REM set JAVA_OPTS=-Dfile.encoding=UTF-8 -Dspring.output.ansi.enabled=ALWAYS
mvn spring-boot:run
pause 