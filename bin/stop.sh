#!/bin/sh
APP_NAME=mall-web
echo "=====停止原有进程====="
#set -x
CMD=`ps -ef|grep Dflag=${APP_NAME}|grep -v grep|grep -v tail|awk 'BEGIN{printf "kill "}{printf "%s ", $2}'|bash`
#	echo ${CMD}
	eval ${CMD}
sleep 1