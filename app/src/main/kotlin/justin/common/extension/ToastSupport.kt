package justin.common.extension

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * @author justin on 2017/03/31 15:02
 * @version V1.0
 */

fun Context.toast(msg: CharSequence): Toast {
    val t = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

fun Context.toast(@StringRes msg: Int): Toast {
    val t = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

fun android.app.Fragment.toast(@StringRes msg: Int): Toast {
    val t = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

fun android.app.Fragment.toast(msg: CharSequence): Toast {
    val t =  Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

fun android.support.v4.app.Fragment.toast(@StringRes msg: Int): Toast {
    val t = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

fun android.support.v4.app.Fragment.toast(msg: CharSequence): Toast {
    val t = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
    t.show()
    return t
}

