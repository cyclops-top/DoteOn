package justin.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @author justin on 2017/03/31 14:56
 * @version V1.0
 */


fun Context.buildIntent(clazz: Class<out Activity>):Intent{
    return Intent(this,clazz)
}

