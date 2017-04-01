package justin.common.app

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import justin.common.activityresult.ActivityResult
import justin.common.activityresult.ActivityResultManagerHelper
import justin.common.activityresult.IActivityResultSupport
import justin.common.permission.IPermissionSupport
import justin.common.permission.PermissionManagerHelper

/**
 * @author justin on 2017/03/31 10:57
 * *
 * @version V1.0
 */
abstract class BaseFragment : Fragment(), IPermissionSupport, IActivityResultSupport {

    private var mPermissionManager: PermissionManagerHelper? = null
    private var mActivityResultManager: ActivityResultManagerHelper? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPermissionManager = PermissionManagerHelper(this)
        mActivityResultManager = ActivityResultManagerHelper(this)
        val view = inflater!!.inflate(getLayout(), container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        return mActivityResultManager!!.startActivityForResult(intent, *shares)
    }

    override fun startActivityForResult(intent: Intent, opt: Bundle): Observable<ActivityResult> {
        return mActivityResultManager!!.startActivityForResult(intent, opt)
    }

    @LayoutRes
    abstract fun getLayout(): Int

    open fun bindEvent() {

    }
}
