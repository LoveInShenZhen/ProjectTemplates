# websocket 样例
[点击查看详细文档](http://loveinshenzhen.github.io/#/sz_framework/websocket/websocket_sample)

## 样例准备实现如下的功能需求

* 后端为每个连接的 websocket 客户端分配一个唯一标识
* 连接成功后, 将唯一标识返回给客户端
* 后端提供一个 json api 用于查询当前连接的客户端列表(唯一标识列表, 连接时间)
* 后端提供一个 json api 用于向指定的客户端发送一段文本信息
* 后端提供一个 json api 用于向所有的客户端广播一段文本信息
* 客户端通过 websocket 向后端发送一个请求, 向指定的另外一个客户端发送一段文本信息
* 客户端通过 websocket 向后端发送一个请求, 向所有的客户端广播一段文本信息
* 后端提供一个 json api 用于强制断掉指定的客户端的连接
* 客户端和后端之间的消息是结构化的 JSON 字符串, 方便前后端进行处理

## 实现 websocket 功能的关键步骤
* 继承 **sz.scaffold.websocket.WebSocketHandler** 实现一个 websocket 处理器类  
* 在 **conf/route.websocket** 文件里配置 **websocket path** 和 **handler class**

