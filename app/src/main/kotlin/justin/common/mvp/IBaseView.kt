package mvp

import android.content.Context
import justin.common.permission.IPermissionSupport

/**
 * @author justin on 2016/12/31 22:41
 * @version V1.0
 */
interface IBaseView<P> :IPermissionSupport{
    fun getContext():Context

}