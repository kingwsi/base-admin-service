FROM java:8
# 将当前目录下的jar包复制到docker容器的/目录下
ADD base-admin-service-0.0.1-SNAPSHOT.jar /base-admin-service-0.0.1-SNAPSHOT.jar
# 运行过程中创建一个mall-tiny-docker-file.jar文件
RUN bash -c 'touch /base-admin-service-0.0.1-SNAPSHOT.jar'
# 声明服务运行在8080端口
EXPOSE 8094
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/base-admin-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=test"]
# 指定维护者的名字
MAINTAINER ws
