@ECHO OFF
SETLOCAL
SET MAVEN_VERSION=3.9.11
SET WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip
SET MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%

IF NOT EXIST "%MAVEN_HOME%" (
    ECHO Downloading Maven %MAVEN_VERSION%...
    mkdir "%MAVEN_HOME%"
    powershell -Command "Invoke-WebRequest -Uri %WRAPPER_URL% -OutFile '%MAVEN_HOME%\maven.zip'"
    powershell -Command "Expand-Archive -Force '%MAVEN_HOME%\maven.zip' '%MAVEN_HOME%'"
    del "%MAVEN_HOME%\maven.zip"
)

SET JAVA_CMD=java
IF DEFINED JAVA_HOME SET JAVA_CMD=%JAVA_HOME%\bin\java.exe
IF NOT EXIST "%JAVA_CMD%" (
    ECHO Error: JAVA_HOME is not set and no 'java' command found in PATH.
    EXIT /B 1
)

"%MAVEN_HOME%\apache-maven-%MAVEN_VERSION%\bin\mvn.cmd" %*
ENDLOCAL
