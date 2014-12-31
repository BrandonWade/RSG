REM @echo off

call SetClasspath

REM @echo on

IF "%1" NEQ "" (SET SLEEP=%1) ELSE (SET SLEEP=1)

cd database
call RestoreDB.bat
cd..

cd ATs
rename "RSG1.act~" "RSG1.act"
rename "RSG2.act"  "RSG2.act~"
rename "RSG3.act"  "RSG3.act~"
cd..

java -cp %CLASSPATH% acceptanceTests.TestRunner %SLEEP%
cd ATs
rename "RSG1.act"  "RSG1.act~"
rename "RSG2.act~"  "RSG2.act"
rename "RSG3.act"  "RSG3.act~"
cd..
java -cp %CLASSPATH% acceptanceTests.TestRunner %SLEEP%
cd ATs
rename "RSG1.act"  "RSG1.act~"
rename "RSG2.act"  "RSG2.act~"
rename "RSG3.act~"  "RSG3.act"
cd..
java -cp %CLASSPATH% acceptanceTests.TestRunner %SLEEP%
cd ATs
rename "RSG1.act"  "RSG1.act~"
rename "RSG2.act"  "RSG2.act~"
rename "RSG3.act"  "RSG3.act~"
cd..
pause