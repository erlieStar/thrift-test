@echo off
title gen code by thrift 

for /f %%i in ('dir /B *.thrift ') do thrift-0.10.0.exe -v -r --gen java  -out ../src/main/java/ %%i

@pause