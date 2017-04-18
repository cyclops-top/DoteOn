package justin.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.util.Size
import android.view.WindowManager


/**
 * @author justin on 2017/03/31 14:56
 * @version V1.0
 */


fun Context.buildIntent(clazz: Class<out Activity>): Intent {
    return Intent(this, clazz)
}

fun Context.getScrrenSize(): Size {
    val wm = getSystemService(Context.WINDOW_SERVICE)!! as WindowManager
    val p = Point()
    wm.defaultDisplay.getSize(p)
    return Size(p.x, p.y)
}
