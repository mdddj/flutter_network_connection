
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterNetworkConnection {
  static const MethodChannel _channel =
      const MethodChannel('flutter_network_connection');

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

  /// 开始检测
  static Future<void> start(String address) async {
    var data = <dynamic,dynamic>{};
    data['address'] = address;
    await _channel.invokeMethod('startTesting',data);
  }

}
