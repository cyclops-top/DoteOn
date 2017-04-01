package justin.common.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import justin.common.activityresult.ActivityResult
import justin.common.activityresult.ActivityResultManagerHelper
import justin.common.activityresult.IActivityResultSupport
import justin.common.extension.toast
import justin.common.permission.IPermissionSupport
import justin.common.permission.PermissionManagerHelper

/**
 * @author justin on 2017/03/31 10:57
 * *
 * @version V1.0
 */
abstract class BaseActivity : AppCompatActivity(), IPermissionSupport, IActivityResultSupport {

    private var mPermissionManager: PermissionManagerHelper? = null
    private var mActivityResultManager: ActivityResultManagerHelper? = null
    private var mToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPermissionManager = PermissionManagerHelper(this)
        mActivityResultManager = ActivityResultManagerHelper(this)
        setContentView(getLayout())
        bindEvent()
    }

    override fun requestPermission(vararg permission: String): Observable<Boolean> {
        return mPermissionManager!!.requestPermission(*permission)
    }

    override final fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mActivityResultManager!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun startActivityForResult(intent: Intent): Observable<ActivityResult> {
        return mActivityResultManager!!.startActivityForResult(intent)
    }

    override fun startActivityForResult(intent: Intent, vararg shares: View): Observable<ActivityResult> {
        return mActivityResultManager!!.startActivityForResult(intent,*shares)
    }

    override fun startActivityForResult(intent: Intent, opt: Bundle): Observable<ActivityResult> {
        return mActivityResultManager!!.startActivityForResult(intent,opt)
    }

    abstract fun getLayout(): Int
    open fun bindEvent() {

    }

    fun toast(msg: String) {
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }

    fun toast(@StringRes msg: Int) {
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }

}
