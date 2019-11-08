# ProjectTemplates
* 基于 Kotlin 的常用的项目工程模板
* 工程是基于 **gradle 5.x** 的版本构建的
* **注意:** 请将下文中的 **{{project_name}}** 替换成你要创建的应用的名称

## 项目模板列表

### Kotlin Application
```bash
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/kotlin-app {{project_name}}

cd {{project_name}}

# 运行命令如下
gradle run

# 调试命令如下, 调试端口 5005, 
# (注: 此为gradle下用IDE进行 remote debug的通用方法,以下其他工程不再复述)
gradle run --debug-jvm

```

### Kotlin Library
```bash
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/kotlin-lib {{project_name}}

```

### Kotlin Multi Projects
```bash
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/kotlin-multi-project {{project_name}}

cd {{project_name}}

gradle :prj-app:run

```

### Multi Projects 下新增一个子工程 (Application类型)
```bash
cd {{root_project}}

svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/empty-app {{project_name}}

echo 'include(":{{project_name}}")' >> settings.gradle.kts

```

### Multi Projects 下新增一个子工程 (Library类型)
```bash
cd {{root_project}}

svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/empty-lib {{project_name}}

echo 'include(":{{project_name}}")' >> settings.gradle.kts

```

### 基于 [sz-vertx-kotlin](https://github.com/LoveInShenZhen/sz-vertx-kotlin) 的 web api server 项目


#### 构建步骤

1 首先在本地构建 sz-vertx-kotlin, 并发布包到 localMaven

```bash
git clone https://github.com/kklongming/sz-vertx-kotlin.git

cd sz-vertx-kotlin

# 发布到本地的 mvaven 仓库, 我们的应用会通过本地的 maven 仓库添加 sz-vertx-kotlin 的依赖jar包
gradle publishToMavenLocal 

```

2 按照如下的指令, 创建单一工程的简单应用或者包含多个子工程的应用

* 单一工程的简单应用
```bash
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/vertx-web-simple {{project_name}}
```

* 包含多个子工程的的应用
```bash
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/branches/v2.0.0/vertx-web-mutli {{project_name}}
```
