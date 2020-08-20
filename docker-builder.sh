#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=base-admin-service
#操作/项目路径(Dockerfile存放的路劲)
BASE_PATH=/var/lib/jenkins/dockerbuild/$SERVER_NAME
# 源jar路径  即jenkins构建后存放的路径
SOURCE_PATH=/var/lib/jenkins/workspace

echo "移动 $SOURCE_PATH/$SERVER_NAME/admin-service/target/admin-service-0.0.1-SNAPSHOT.jar 至 $BASE_PATH ...."
#把项目从jenkins构建后的目录移动到我们的项目目录下同时重命名下
mv $SOURCE_PATH/$SERVER_NAME/admin-service/target/admin-service-0.0.1-SNAPSHOT.jar $BASE_PATH/admin-service-0.0.1-SNAPSHOT.jar
#移动 Dockerfile
mv $SOURCE_PATH/$SERVER_NAME/admin-service/Dockerfile $BASE_PATH
echo "完成"

#容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')

# 构建docker镜像
if [ -n "$IID" ]; then
        echo "删除旧$SERVER_NAME镜像，IID=$IID"
        docker rmi $IID
        echo "已删除，重新构建镜像"
        cd $BASE_PATH
        docker build -t $SERVER_NAME .
else
        echo "不存在$SERVER_NAME镜像，开始构建镜像"
        cd $BASE_PATH
        docker build -t $SERVER_NAME .
        echo "构建完成！即将运行$SERVER_NAME"
fi
# 停掉原来的
if [ -n "$CID" ]; then
        echo "正在停止$SERVER_NAME，CID=$CID"
        docker stop $CID
        echo "已停止！"
else
        echo "容器 $SERVER_NAME 未运行"
fi

# 运行docker容器
docker run \
--name $SERVER_NAME\
--rm \
--restart=always \
-d\
-p 8094:8094\
$SERVER_NAME

echo "$SERVER_NAME 已启动"