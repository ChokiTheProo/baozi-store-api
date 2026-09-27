@echo off
REM Inicia a API usando MySQL. Troque a senha abaixo pela senha do root do seu MySQL.
pushd "%~dp0"
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "MYSQL_USER=root"
set "MYSQL_PASSWORD=root"
call "%~dp0mvnw.cmd" spring-boot:run -Dspring-boot.run.profiles=mysql
pause
