# ProjectTemplates
* 基于 Kotlin 的常用的项目工程模板
* 工程是基于 gradle 5.x 的版本构建的

## 项目模板列表

### Kotlin Application
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-app {{project_name}}

cd {{project_name}}

# 运行命令如下
gradle run

# 调试命令如下, 调试端口 5005
gradle run --debug-jvm

```

### Kotlin Library
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-lib {{project_name}}

```

### Kotlin Multi Projects
```
svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/kotlin-multi-project {{project_name}}

cd {{project_name}}

gradle :prj-app:run

```

### 新增一个子项目 (Application类型)
```
cd {{root_project}}

svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/empty-app {{project_name}}

echo 'include(":{{project_name}}")' >> settings.gradle.kts

```

### 新增一个子项目 (Library类型)
```
cd {{root_project}}

svn export https://github.com/LoveInShenZhen/ProjectTemplates.git/trunk/empty-lib {{project_name}}

echo 'include(":{{project_name}}")' >> settings.gradle.kts

```
