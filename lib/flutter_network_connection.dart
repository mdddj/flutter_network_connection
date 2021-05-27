import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class FlutterNetworkConnection {
  FlutterNetworkConnection._();

  static FlutterNetworkConnection get instance => FlutterNetworkConnection._();

  factory FlutterNetworkConnection() => instance;

  static const MethodChannel _channel = const MethodChannel('flutter_network_connection');

  static const EventChannel _eventChannel = const EventChannel('flutter_network_connection_eventkey');

  late Stream<String> stream;
  StreamSubscription? _streamSubscription;

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
  Future<void> start(String address, {ValueChanged<String>? sourceData}) async {
    var params = <dynamic, dynamic>{};
    params['address'] = address;
    _streamSubscription = _createListingStream().listen((event) {
      sourceData?.call(event);
    }, onDone: closeListingStream);
    await _channel.invokeMethod('startTesting', params);
  }

  /// 开始检测网络url
  ///
  /// [address]  合法的url
  ///
  /// 例子：http://baidu.com
  ///
  /// [type] 返回的数据类型
  ///
  /// [sourceData] 源数据，是个json字符串
  ///
  ///
  Future<void> startWithType(String address, {String? type,ValueChanged<String>? sourceData}) async {
    var params = <dynamic,dynamic>{};
    params['address'] = address;
    if(type!=null){
      params['type'] = type;
    }
    _streamSubscription = _createListingStream().listen((event) {
      sourceData?.call(event);
    }, onDone: closeListingStream);
    await _channel.invokeMethod('startTestingWithType',params);
  }

  /// 创建监听流,和原生的数据传输通道
  Stream<String> _createListingStream() {
    stream = _eventChannel.receiveBroadcastStream().cast<String>();
    return stream;
  }

  /// 关闭监听流
  void closeListingStream() {
    if (_streamSubscription != null) {
      _streamSubscription?.cancel();
      _streamSubscription = null;
    }
  }
}
