package shop.itbug.flutter_network_connection

import android.content.Context
import android.util.Log
import com.facebook.network.connectionclass.ConnectionClassManager
import com.facebook.network.connectionclass.DeviceBandwidthSampler
import fairy.easy.httpmodel.model.HttpNormalUrlLoader

import fairy.easy.httpmodel.HttpModelHelper
import fairy.easy.httpmodel.resource.HttpListener


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
//        Log.i("ConnectUtil","开始检测:$address")
        HttpModelHelper.getInstance()
                .init(context)
                .setChina(true)
                .setModelLoader(HttpNormalUrlLoader())
                .setFactory()
                .addAll()
                .build()
                .startAsync(address, httpListener)


    }

}