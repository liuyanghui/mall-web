#!/bin/sh
BASE=/usr/mpsp/mall-web
CLASSPATH=${BASE}/classes
LIB_BASE=${BASE}/lib
for file in ${LIB_BASE}/*.jar
do
CLASSPATH=$CLASSPATH:$file
done
echo ${CLASSPATH}

#JAVA=/usr/lib/jdk1.7.0_79/bin/java
JAVA=/usr/lib/jdk1.7.0_79/bin/java

APP_NAME=mall-web
APP_PORT=8181
OPT1=" -Dflag=${APP_NAME} -Dfile.encoding=utf-8"
OPT2=" -Xms128m -Xmx1024m -XX:PermSize=64M -XX:MaxPermSize=128M"


CMD=`nohup ${JAVA} ${OPT1} ${OPT2} -cp ${CLASSPATH} com.mall.App ${APP_PORT} >> console.out 2>&1 &`
echo ${CMD}
#eval ${CMD}
sleep 5

echo ">>> netstat -anp | grep ${APP_PORT}"

netstat -anp|grep ${APP_PORT}

tail -50f console.out
