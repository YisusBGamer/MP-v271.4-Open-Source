@echo off
rem Exports the game data to XML for the server. Drag your MapleStory folder onto this file, or run:
rem   "Export XML.bat" "C:\Program Files (x86)\Steam\steamapps\common\MapleStory"
setlocal
set "CLIENT=%~1"
if "%CLIENT%"=="" set "CLIENT=C:\Program Files (x86)\Steam\steamapps\common\MapleStory"
set "OUT=%~dp0..\wz"
set DOTNET_ReadyToRun=0
dotnet run --project "%~dp0wzexport.csproj" -c Release -- "%CLIENT%" "%OUT%" --threads 4
echo.
echo Done (code %errorlevel%). The export is in the wz folder next to the server. Errors, if any: wz\_export-errors.txt
pause
