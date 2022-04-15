#文件名称
RESOURCE_NAME=redisMemory-0.0.1-SNAPSHOT.jar
BASEPATH=../lib/
#停止服务
PID=$(ps -ef | grep $RESOURCE_NAME  | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo VBank Application is already stopped
else

    echo kill $PID
    kill $PID
fi

echo $BASEPATH$RESOURCE_NAME
#启动服务
nohup java -jar $BASEPATH$RESOURCE_NAME 一server. port=8080  > nohup.log 2>&1 &

sleep 30
tail -f nohup.log