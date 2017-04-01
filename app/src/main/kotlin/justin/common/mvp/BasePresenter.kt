package mvp

import io.reactivex.processors.BehaviorProcessor

/**
 * @author justin on 2016/08/31 15:39
 * *         justin@magicare.me
 * *
 * @version V1.0
 */
open class BasePresenter<out V : IView>(open val view: V) {

    enum class STATE {
        INIT, RELEASE;
    }

    val life: BehaviorProcessor<STATE> = BehaviorProcessor.create()

    fun init() {
        life.onNext(STATE.INIT)
    }

    fun release() {
        life.onNext(STATE.RELEASE)
    }

}
