package justin.common.app

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.Flowable
import justin.common.extension.toast
import justin.common.permission.IPermissionSupport
import justin.common.permission.PermissionManagerHelper

/**
 * @author justin on 2017/03/31 10:57
 * *
 * @version V1.0
 */
abstract class BaseActivity : AppCompatActivity(), IPermissionSupport {

    private var mPermissionManager: PermissionManagerHelper? = null
    private var mToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPermissionManager = PermissionManagerHelper(this)
        setContentView(getLayout())
        bindEvent()
    }

    override fun requestPermission(vararg permission: String): Flowable<Boolean> {
        return mPermissionManager!!.requestPermission(*permission)
    }

    override final fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    abstract fun getLayout():Int
    open fun bindEvent(){

    }

    fun toast(msg:String){
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }
    fun toast(@StringRes msg:Int){
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }

}
