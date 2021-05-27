
import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class FlutterNetworkConnection {
  static const MethodChannel _channel =
      const MethodChannel('flutter_network_connection');
  
  static const EventChannel _eventChannel = const EventChannel('flutter_network_connection_eventkey');

  static Stream<String>? _stream;


  /// 开始采样
  static Future<void> startSampling() async {
    await _channel.invokeMethod('startSampling');
  }

  /// 停止采样
  static Future<void> stopSampling() async {
    await _channel.invokeMethod('stopSampling');
  }

  /// 获取当前网络质量
  static Future<String> getCurrentBandwidthQuality() async {
    return await _channel.invokeMethod('getCurrentBandwidthQuality');
  }

  /// 获取当前下载速度
  static Future<double> getDownloadKBitsPerSecond() async {
    return await _channel.invokeMethod('getDownloadKBitsPerSecond');
  }

  /// 开始检测网络url
  ///
  /// [address]  合法的url
  ///
  /// 例子：http://baidu.com
  ///
  /// [sourceData] 源数据，是个json字符串
  ///
  ///
  static Future<void> start(String address,{ValueChanged<String>? sourceData}) async {
    var data = <dynamic,dynamic>{};
    data['address'] = address;
    _listingStream();
    if(_stream!=null){
      _stream!.listen((event) {
        print('收到流数据：$event');
        sourceData?.call(event);
      });
    }

    await _channel.invokeMethod('startTesting',data);

  }


  /// 监听流
  static void _listingStream(){
   _stream = _eventChannel.receiveBroadcastStream().cast<String>();
  }

}
