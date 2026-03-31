@echo off
cd /d "%~dp0"
echo Stopping all Java processes...
taskkill /F /IM java.exe 2>nul
taskkill /F /IM javaw.exe 2>nul
timeout /t 3 /nobreak
echo Starting application on port 9090...
java -jar "target\snaker-0.0.1-SNAPSHOT.jar" --server.port=9090
pause

