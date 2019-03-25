# ProjectTemplates
* 基于 Kotlin 的常用的项目工程模板
* 工程是基于 gradle 5.x 的版本构建的

## 项目模板列表

### Kotlin Application
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-app

cd kotlin-app

# 运行命令如下
gradle run

# 调试命令如下, 调试端口 5005
gradle run --debug-jvm

```

### Kotlin Library
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-lib

```

### Kotlin Multi Projects
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-multi-project

cd kotlin-multi-project

gradle :prj-app:run

```
