package justin.common.app

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import justin.common.permission.IPermissionSupport
import justin.common.permission.PermissionManagerHelper

/**
 * @author justin on 2017/03/31 10:57
 * *
 * @version V1.0
 */
abstract class BaseFragment : Fragment(), IPermissionSupport {

    private var mPermissionManager: PermissionManagerHelper? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPermissionManager = PermissionManagerHelper(this)
        val view = inflater!!.inflate(getLayout(),container,false)
        return view
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvent()
    }
    override fun requestPermission(vararg permission: String): Flowable<Boolean> {
        return mPermissionManager!!.requestPermission(*permission)
    }

    override final fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    @LayoutRes
    abstract fun getLayout(): Int
    open fun bindEvent(){

    }
}
