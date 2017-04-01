package justin.common.mvp

import android.content.Context
import android.os.Bundle
import justin.common.app.BaseActivity
import mvp.BasePresenter
import mvp.IBaseView
import mvp.IView
import mvp.MVPHelper

/**
 * @author justin on 2017/03/31 16:56
 * @version V1.0
 */
open class MVPActivity<P : BasePresenter<*>> : BaseActivity(), IBaseView<P>, IView {

    private var helper: MVPHelper<P>? = null
    val presenter:P
        get() = helper!!.presenter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        helper = MVPHelper(this)
        super.onCreate(savedInstanceState)
        presenter.init()
    }

    override fun onDestroy() {
        presenter.release()
        super.onDestroy()
    }

    override fun getContext(): Context {
        return this
    }

    override fun getLayout(): Int = helper!!.layout


}

