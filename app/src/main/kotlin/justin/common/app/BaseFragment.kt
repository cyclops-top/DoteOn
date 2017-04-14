package justin.common.app

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.Observable
import justin.common.activityresult.ActivityResult
import justin.common.activityresult.ActivityResultManagerHelper
import justin.common.activityresult.IActivityResultSupport
import justin.common.extension.toast
import justin.common.permission.IPermissionSupport
import justin.common.permission.PermissionManagerHelper
import justin.doteon.R

/**
 * @author justin on 2017/03/31 10:57
 * *
 * @version V1.0
 */
abstract class BaseFragment : Fragment(), IPermissionSupport, IActivityResultSupport,
        IProgressSupport,IToastSupport {

    private var mPermissionManager: PermissionManagerHelper? = null
    private var mActivityResultManager: ActivityResultManagerHelper? = null
    private var mToast: Toast? = null
    private var process: Dialog? = null

    private var isInitDone = false
    private var isDestroy = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPermissionManager = PermissionManagerHelper(this)
        mActivityResultManager = ActivityResultManagerHelper(this)
        return viewCreate(inflater,container,savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isInitDone = true
        isDestroy = false
        bindEvent()
        lazyLoad()
    }

    open fun viewCreate(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater!!.inflate(getLayout(), container, false)
    }

    override fun onDestroyView() {
        isDestroy = true
        super.onDestroyView()
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

    open fun lazyLoad(){

    }

   override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        checkIsNeedLazyLoad()
    }

    protected fun checkIsNeedLazyLoad() {
        if (isInitDone && !isDestroy && userVisibleHint) {
            lazyLoad()
        }
    }


    override fun toast(msg: String) {
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }

    override fun toast(@StringRes msg: Int) {
        mToast?.cancel()
        mToast = (this as Context).toast(msg)
    }

    override fun showProgress() {
        showProgress(false)
    }
    override fun showProgress(cancelable: Boolean) {
        if(process == null) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            @SuppressLint("InflateParams")
            val progressBar = inflater.inflate(R.layout.progress, null) as ProgressBar
            builder.setView(progressBar).setCancelable(cancelable)
            process = builder.create()
        }
        //noinspection ConstantConditions
        process!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        process!!.show()
    }

    override fun dismissProgress() {
        if(process != null){
            process!!.dismiss()
        }
    }
}
