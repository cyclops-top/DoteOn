package justin.common.extension

import android.util.Log

/**
 * @author justin on 2017/04/01 10:14
 * *
 * @version V1.0
 */
fun Any.logd(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}

fun Any.logi(msg: String) {
    Log.i(this.javaClass.simpleName, msg)
}

fun Any.loge(msg: String) {
    Log.e(this.javaClass.simpleName, msg)
}

fun Any.logv(msg: String) {
    Log.v(this.javaClass.simpleName, msg)
}