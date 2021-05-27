package shop.itbug.flutter_network_connection

import android.content.Context
import com.facebook.network.connectionclass.ConnectionClassManager
import com.facebook.network.connectionclass.DeviceBandwidthSampler
import fairy.easy.httpmodel.model.HttpNormalUrlLoader

import fairy.easy.httpmodel.HttpModelHelper
import fairy.easy.httpmodel.resource.HttpListener
import fairy.easy.httpmodel.resource.HttpType


/**
 * 连接工具类
 */
class ConnectUtil {

    /**
     * 开始采样
     */
    fun startSampling() {
        DeviceBandwidthSampler.getInstance().startSampling()
    }

    /**
     * 停止采样
     */
    fun stopSampling() {
        DeviceBandwidthSampler.getInstance().stopSampling()
    }

    /**
     * 获取当前网络质量
     */
    fun getCurrentBandwidthQuality(): String {
        val currentBandwidthQuality = ConnectionClassManager.getInstance().currentBandwidthQuality
        if (currentBandwidthQuality != null) {
            return currentBandwidthQuality.toString();
        }
        return "unknown"
    }

    /**
     * 获取当前下载速度
     */
    fun getDownloadKBitsPerSecond(): Double {
        return ConnectionClassManager.getInstance().downloadKBitsPerSecond
    }

    /**
     * 开始检测
     */
    fun start(address: String, context: Context, httpListener: HttpListener) {
        HttpModelHelper.getInstance()
                .init(context)
                .setChina(true)
                .setModelLoader(HttpNormalUrlLoader())
                .setFactory()
                .addAll()
                .build()
                .startAsync(address, httpListener)


    }

    /**
     * 开始检测
     * 根据type 类型
     */
    fun startWithType(address: String, context: Context, httpListener: HttpListener, typeString: String) {

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

        HttpModelHelper.getInstance()
                .init(context)
                .setChina(true)
                .setModelLoader(HttpNormalUrlLoader())
                .setFactory()
                .addType(type)
                .build()
                .startAsync(address, httpListener)
    }


}