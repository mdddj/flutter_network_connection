package shop.itbug.flutter_network_connection

import com.google.gson.Gson
import fairy.easy.httpmodel.resource.HttpListener
import fairy.easy.httpmodel.resource.HttpType
import io.flutter.plugin.common.EventChannel
import org.json.JSONObject

class ResultListing(private val sink: EventChannel.EventSink) : HttpListener {


    override fun onSuccess(httpType: HttpType?, result: JSONObject?) {
        val map = mutableMapOf<String, String>()
        map["type"] = httpType.toString()
        map["data"] = result.toString()
        map["status"] = "success"
        val json = Gson().toJson(map)
        sink.success(json)
    }

    override fun onFail(data: String?) {
        val map = mutableMapOf<String, String>()
        map["type"] = "ERROR"
        map["data"] = data ?: "开启失败"
        map["status"] = "ERROR"
        val json = Gson().toJson(map)
        sink.success(json)
    }

    override fun onFinish(result: JSONObject?) {
        val map = mutableMapOf<String, String>()
        map["type"] = "FINISH"
        map["data"] = result.toString()
        map["status"] = "FINISH"
        val json = Gson().toJson(map)
        sink.success(json)
        sink.endOfStream()
    }
}