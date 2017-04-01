package justin.common.activityresult

import android.app.Activity
import android.content.Intent

/**
 * @author justin on 2017/04/01 10:46
 * @version V1.0
 */


class ActivityResult(val resultCode: Int, val data: Intent?) {
    val isOk = resultCode == Activity.RESULT_OK
    val isCanceled  = resultCode == Activity.RESULT_CANCELED

    override fun toString(): String {
        return "ActivityResult( result=$resultCode, data=$data)"
    }

}
