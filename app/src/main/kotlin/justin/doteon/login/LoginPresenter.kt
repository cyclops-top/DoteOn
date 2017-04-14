package justin.doteon.login

import io.reactivex.Observable
import mvp.BasePresenter

/**
 * @author justin on 2017/03/31 17:06
 * @version V1.0
 */

class LoginPresenter(view: ILoginView) : BasePresenter<ILoginView>(view) {
    fun login(user: String, pwd: String): Observable<Boolean>{
        return Observable.empty()
    }
}
