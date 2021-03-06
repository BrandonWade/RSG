REM @echo off

REM
REM This script requires that the "javac" command be on the system command path.
REM This can be accomplished by modifying the path statement below
REM to include "C:\Program Files\Java\jdk1.6.0_03\bin" (or whatever your path is).
REM Alternatively, you could add the path to "javac" to the PATH Environment variable: 
REM   Settings-->Control Panel-->System-->Advanced-->Environment Variables-->Path
REM


REM clean all .class files out of the bin directory

cd bin
erase /S /Q *.class
cd ..


call SetClasspath


REM @echo on

REM javac -d bin\ -cp %classpath% src\domainobjects\*.java

REM javac -d bin\ -cp %classpath% src\persistence\*.java

javac -d bin\ -cp %classpath% src\rsg\objects\*.java src\rsg\persistence\*.java src\rsg\services\*.java src\rsg\presentation\*.java src\rsg\processing\*.java src\org\eclipse\wb\swt\*.java
javac -d bin\ -cp %classpath% src\tests\*.java src\rsg\services\*.java src\tests\objects\*.java src\tests\persistence\*.java src\tests\presentation\*.java src\tests\processing\*.java src\tests\integration\*.java

pause
