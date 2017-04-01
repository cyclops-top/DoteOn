package justin.common.extension

import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.view.View

/**
 * @author justin on 2017/04/01 14:19
 * @version V1.0
 */

fun Fragment.makeShareViewOptions(vararg shares: View): Bundle {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val scenes = arrayOfNulls<Pair<View, String>>(shares.size)
        for ((index, v) in shares.withIndex()) {
            val transitionName = v.transitionName
            val pair = Pair(v, transitionName!!)
            scenes[index] = pair
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *scenes).toBundle()
    } else {
        return Bundle()
    }
}
