@echo off
echo -------------------------------------------------------------------------------
echo COMPILATION AND STATIC CODE ANALYSIS FOR DUBAI PROJECTS
echo -------------------------------------------------------------------------------
echo.

set hasErrorsCompiling=0
set stopOnFail=%2

set as45_java_version=1.6
set customization_java_version=1.8
set ssavpbackend_java_version=1.8
set mapper_java_version=1.6

if "%1" == "" (
	echo Usage. dubai.bat [as45, customization, backend, mapper, all] [stop]
) else if "%1" == "as45" (
	call :analizeAs45
) else if "%1" == "customization" (
	call :analizeCustomization
) else if "%1" == "backend" (
	call :analizeSsavpBackend
) else if "%1" == "mapper" (
	call :analizeSsavpProductMapper
) else if "%1" == "all" (
	call :analizeAll
) else (
	echo %1 option not valid for Dubai.
)
exit /B %errorlevel%

:analizeAs45
call :analizeJavaCode as45 %as45_java_version%
exit /B 0

:analizeCustomization
call :analizeJavaCode customization %customization_java_version%
exit /B 0

:analizeSsavpBackend
call :analizeJavaCode ssavp-backend %ssavpbackend_java_version%
exit /B 0

:analizeSsavpProductMapper
call :analizeJavaCode ssavp-product-mapper %mapper_java_version%
exit /B 0

:analizeAll
call :analizeAs45
call :analizeCustomization
call :analizeSsavpBackend
call :analizeSsavpProductMapper
exit /B 0

:analizeJavaCode
echo -------------------------------------------------------------------------------
echo %1 Static code analysis with Fortify. Java version %2
echo -------------------------------------------------------------------------------
cd %1
call mvn clean compile -fae -DskipTests -Dmaven.site.skip=true || set hasErrorsCompiling=1

set stop="no"
if "%stopOnFail%" == "stop" (
	if %hasErrorsCompiling% == 1 (
		set stop="yes"
	)
)

if %stop% == "yes" (
	echo Review the code of the %1 project. The MAVEN build has failed
	set hasErrorsCompiling=0
) else (
	call mvn com.hpe.security.fortify.maven.plugin:sca-maven-plugin:clean
	call mvn com.hpe.security.fortify.maven.plugin:sca-maven-plugin:translate -Dfortify.sca.verbose=true -Dfortify.sca.source.version=%2 -Dfortify.sca.sqlType=TSQL
	call mvn com.hpe.security.fortify.maven.plugin:sca-maven-plugin:scan -Dfortify.sca.verbose=true
)
cd ..
echo -------------------------------------------------------------------------------
echo.
exit /B 0