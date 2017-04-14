package justin.common.app

import android.support.annotation.StringRes

/**
 * @author justin on 2017/04/08 12:35
 * @version V1.0
 */

interface IProgressSupport {
    fun showProgress()
    fun showProgress(cancelable:Boolean)
    fun dismissProgress()
}