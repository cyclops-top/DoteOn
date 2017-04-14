package justin.common.app

import android.support.annotation.StringRes

/**
 * @author justin on 2017/04/08 12:37
 * @version V1.0
 */
interface IToastSupport{
    fun toast(msg: String)
    fun toast(@StringRes msg: Int)
}