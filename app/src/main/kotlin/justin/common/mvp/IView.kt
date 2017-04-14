package mvp

import android.content.Context
import justin.common.app.IProgressSupport
import justin.common.app.IToastSupport

/**
 * @author justin on 2017/01/01 02:31
 * @version V1.0
 */
interface IView : IProgressSupport, IToastSupport {
    fun getContext(): Context
}