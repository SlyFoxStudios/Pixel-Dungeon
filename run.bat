@echo off
color 0a
cls
:start
cls
echo MADE BY BRAYDEN MOON AKA CRAZYWOLF
echo>nul
echo>nul
echo>nul
echo.
echo>nul
echo>nul
echo>nul
echo>nul
echo [0] Desktop Run
echo [1] Desktop Compile
echo [2] Android Run
echo [3] Android Compile
echo.
echo.
echo.
echo [8] Credits
echo [9] Exit
echo.
echo.
set /p input= You have chosen?
if %input%==0 goto Desktoprun if NOT goto start
if %input%==1 goto DesktopCompile if NOT goto start
if %input%==2 goto AndroidRun if NOT goto start
if %input%==3 goto AndroidCompile if NOT goto start

if %input%==8 goto Credits if NOT goto start
if %input%==9 goto MainExit if NOT goto start
echo "%input%" is not a valid option. Please try again.
cls
echo
goto start
cls

:Desktoprun
cls
gradlew desktop:run

:DesktopCompile
cls
gradlew desktop:dist

:AndroidRun
cls
gradlew android:run

:AndroidCompile
cls
::what ever the android command is...