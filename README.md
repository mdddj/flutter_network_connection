# flutter_network_connection

flutter 检测网络速度



## 引入插件

![](https://badgen.net/pub/v/flutter_network_connection) [flutter_network_connection](https://pub.dev/packages/flutter_network_connection)

```dart
  flutter_network_connection: ^0.0.4

```

## 检测网络地址的速度数据

1.普通模式，默认返回全部检测数据
```dart
FlutterNetworkConnection.start('http://baidu.com');
```

2. 返回特定类型的检测数据
 ```dart
   FlutterNetworkConnection.startWithType('http://baidu.com',type:'Ping');
```
   type可选的类型如下
```kotlin
   val type: HttpType = when (typeString) {
   "Index" -> HttpType.INDEX
   "Ping" -> HttpType.PING
   "Http" -> HttpType.HTTP
   "Host" -> HttpType.HOST
   "PortScan" -> HttpType.PORT_SCAN
   "MtuScan" -> HttpType.MTU_SCAN
   "TraceRoute" -> HttpType.TRACE_ROUTE
   "NsLookup" -> HttpType.NSLOOKUP
   "Net" -> HttpType.NET
   else -> HttpType.PING
   }
```

