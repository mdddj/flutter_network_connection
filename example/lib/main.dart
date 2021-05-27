import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_network_connection/flutter_network_connection.dart';
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  // 测速点
  final _url = 'http://speedtest-sgp1.digitalocean.com/1gb.test';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: SingleChildScrollView(
            child: Column(
              children: [
                MaterialButton(
                  onPressed: () async {
                    Directory tempDir = await getTemporaryDirectory();
                    String tempPath = tempDir.path;
                    Dio().download(_url, tempPath);
                    await FlutterNetworkConnection.startSampling();
                    final result = await FlutterNetworkConnection.getCurrentBandwidthQuality();
                    print('当前网络质量：' + result);
                  },
                  child: Text('获取当前网络质量'),
                ),
                MaterialButton(
                  onPressed: () async {
                    final result = await FlutterNetworkConnection.getDownloadKBitsPerSecond();
                    print(result);
                  },
                  child: Text('测速'),
                ),
                TextButton(onPressed: (){
                  FlutterNetworkConnection.start('http://luker.tech/admin');
                }, child: Text('开始检测'))
              ],
            ),
          )),
    );
  }
}
