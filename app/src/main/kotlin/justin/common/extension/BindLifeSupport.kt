package justin.common.extension

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import mvp.BasePresenter
import org.reactivestreams.Publisher

/**
 * @author justin on 2017/03/31 16:10
 * @version V1.0
 */

fun <T> Flowable<T>.bindLife(p: BasePresenter<*>) {
    observeOn(AndroidSchedulers.mainThread())
            .compose(NormalCheckLifeCycleTransformer(p.life))
}

class NormalCheckLifeCycleTransformer<T>(val eventBehavior: BehaviorProcessor<BasePresenter.STATE>) :
        FlowableTransformer<T, T> {
    override fun apply(upstream: Flowable<T>?): Publisher<T> {
        return upstream!!.observeOn(AndroidSchedulers.mainThread())
                .takeUntil(eventBehavior.filter { state -> state == BasePresenter.STATE.RELEASE })
    }
}