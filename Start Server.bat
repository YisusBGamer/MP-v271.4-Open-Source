@echo off
rem Starts the Swordie Plus server from this folder (the launcher's cog runs this).
rem Contributed by Arixal (from GitLab).
rem It runs a copy of the newest bin\maplestory-<version>.jar, from run\, so a build can replace the jar in bin\ while
rem the server is up. When the server exits for a restart (control\restart.flag) it starts again, with the newest jar.
cd /d "%~dp0"
if not defined SERVER_JAVA set "SERVER_JAVA=java"
rem tells the server that this loop will bring it back (RestartManager)
set "PLUS_RESTART_LOOP=1"
if exist "control\restart.flag" del "control\restart.flag"

:start
set "JAR="
for /f "delims=" %%J in ('dir /b /o-d "bin\maplestory-*.jar" ^| findstr /v /i /c:"-shaded"') do if not defined JAR set "JAR=bin\%%J"
if not defined JAR (
    echo No bin\maplestory-*.jar here. Build the server first.
    exit /b 1
)
if not exist "run" mkdir "run"
rem the jar can stay in use for a moment after a restart, so give it a few tries
set "TRIES=0"
:copy
copy /y "%JAR%" "run\server.jar" >nul 2>&1 && goto run
set /a TRIES+=1
if %TRIES% lss 3 (
    ping -n 3 127.0.0.1 >nul
    goto copy
)
echo Couldn't copy %JAR% to run\server.jar. Is the server already running?
exit /b 1

:run
"%SERVER_JAVA%" -Xmx8g -jar "run\server.jar"
if exist "control\restart.flag" (
    del "control\restart.flag"
    goto start
)
