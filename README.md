# netty-demo
分别再 Agent 和 Controller 文件夹下执行

```
mvn clean  package assembly:single
```

## dependency

```
cd lib
mvn install:install-file -DgroupId=com.javastruct -DartifactId=javastruct -Dversion=0.1 -Dpackaging=jar -Dfile=javastruct-0.1.jar
```
