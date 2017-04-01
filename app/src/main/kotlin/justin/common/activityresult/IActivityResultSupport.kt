package justin.common.activityresult

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.reactivex.Observable

/**
 * @author justin on 2017/04/01 10:36
 * @version V1.0
 */

interface IActivityResultSupport {
    fun startActivityForResult(intent: Intent): Observable<ActivityResult>
    fun startActivityForResult(intent: Intent, vararg shares: View): Observable<ActivityResult>
    fun startActivityForResult(intent: Intent,  opt: Bundle): Observable<ActivityResult>
}