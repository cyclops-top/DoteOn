package justin.common.permission


import io.reactivex.Observable

/**
 * @author justin on 2017/03/06 09:24
 * *
 * @version V1.0
 */
interface IPermissionSupport {
    fun requestPermission(vararg permission: String): Observable<Boolean>
}
