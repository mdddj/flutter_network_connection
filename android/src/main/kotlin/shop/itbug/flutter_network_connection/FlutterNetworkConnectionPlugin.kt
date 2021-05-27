package shop.itbug.flutter_network_connection

import android.content.Context
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/// 网络检测速度
class FlutterNetworkConnectionPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var context: Context
    private lateinit var channel: MethodChannel
    private lateinit var eventChannel: EventChannel
    private val util: ConnectUtil = ConnectUtil()
    private val eventKey = "flutter_network_connection_eventkey"
    private lateinit var sink: EventChannel.EventSink


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_network_connection")
        channel.setMethodCallHandler(this)
        eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, eventKey)
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                if (events != null) {
                    sink = events
                }
            }

            override fun onCancel(arguments: Any?) {
            }
        })
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "startSampling") {
            util.startSampling()
        } else if (call.method == "startTesting") {
            val address = call.argument<String>("address")
            if (address != null) {
                util.start(address, context, ResultListing(sink))
            } else {
                result.error("10001", "请输入测试地址", "address 参数为null ")
            }
        } else if (call.method == "startTestingWithType") {
            val address = call.argument<String>("address")
            if (address == null) result.error("10001", "请输入测试地址", "address 参数为null ")
            val type = call.argument<String>("type") ?: ""
            if (address != null) {
                util.startWithType(address, context, ResultListing(sink), type)
            }

        } else if (call.method == "stopSampling") {
            util.stopSampling()
        } else if (call.method == "getCurrentBandwidthQuality") {
            result.success(util.getCurrentBandwidthQuality())
        } else if (call.method == "getDownloadKBitsPerSecond") {
            result.success(util.getDownloadKBitsPerSecond())
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


}
