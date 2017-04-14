package justin.common.extension

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import mvp.BasePresenter

/**
 * @author justin on 2017/03/31 16:10
 * @version V1.0
 */

fun <T> Observable<T>.bindLife(p: BasePresenter<*>):Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
            .compose(CheckLifeCycleTransformer(p.life))
            .subscribeOn(AndroidSchedulers.mainThread())
}

class CheckLifeCycleTransformer<T>(val eventBehavior: BehaviorSubject<BasePresenter.STATE>) :
        ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>?): ObservableSource<T> {
        return upstream!!.observeOn(AndroidSchedulers.mainThread())
                .takeUntil(eventBehavior.filter { state -> state == BasePresenter.STATE.RELEASE })
    }
}