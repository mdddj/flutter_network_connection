package shop.itbug.flutter_network_connection

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import fairy.easy.httpmodel.resource.HttpListener
import fairy.easy.httpmodel.resource.HttpType

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.json.JSONObject

/// 网络检测速度
class FlutterNetworkConnectionPlugin : FlutterPlugin, MethodCallHandler {
    private val tag: String = "FlutterNetworkConnectionPlugin"
    private lateinit var context: Context
    private lateinit var channel: MethodChannel
    private val util: ConnectUtil = ConnectUtil()
    private val eventKey = "flutter_network_connection_eventkey"
    private lateinit var sink: EventChannel.EventSink


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_network_connection")
        channel.setMethodCallHandler(this)
        EventChannel(flutterPluginBinding.binaryMessenger, eventKey).setStreamHandler(object : EventChannel.StreamHandler {
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

                util.start(
                        address, context,
                        object : HttpListener {
                            /// 检测成功
                            override fun onSuccess(httpType: HttpType?, result: JSONObject?) {
                                Log.d(tag, "onSuccess类型：${httpType.toString()}")
                                Log.d(tag, "onSuccess返回数据：${result.toString()}")
                            }


                            override fun onFail(data: String?) {
                                Log.e(tag, "onFail:扫描失败：$data")
                            }

                            override fun onFinish(result: JSONObject?) {
                                Log.d(tag, "onFinish：${result.toString()}")
                            }
                        }
                )
                result.success("开启检测成功")
            } else {
                result.error("10001", "请输入测试地址", "address 参数为null ")
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
