package mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import justin.common.app.LoadFragment

/**
 * @author justin on 2016/12/31 22:46
 * @version V1.0
 */
abstract class MVPLoadFragment<P : BasePresenter<*>> : LoadFragment(), IBaseView<P>, IView {
    private var helper: MVPHelper<P>? = null
    val presenter:P
        get() = helper!!.presenter!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        helper = MVPHelper(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun onDestroyView() {
        presenter.release()
        super.onDestroyView()
    }


    override fun getLayout(): Int = helper!!.layout


}
