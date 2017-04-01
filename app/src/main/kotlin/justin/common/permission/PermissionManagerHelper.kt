@file:Suppress("unused")

package justin.common.permission

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author justin on 2017/03/06 09:44
 * *
 * @version V1.0
 */
class PermissionManagerHelper {
    private val mPermissionFlowable = SparseArray<BehaviorProcessor<Boolean>>()
    private val mActivity: Activity?
    private val mFragment: Fragment?

    constructor(activity: Activity) {
        mActivity = activity
        mFragment = null
    }

    constructor(fragment: Fragment) {
        mActivity = null
        mFragment = fragment
    }

    val context: Context
        get() {
            if (mFragment != null) {
                return mFragment.context
            } else {
                return mActivity!!
            }
        }

    fun checkPermission(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    @SuppressLint("NewApi")
    fun requestPermission(vararg permission: String): Flowable<Boolean> {
        val isAllow = checkPermission(*permission)
        val Flowable: BehaviorProcessor<Boolean>
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || isAllow) {
            Flowable = BehaviorProcessor.createDefault(isAllow)
        } else {
            val requestCode = sAtomicInteger.getAndIncrement()
            Flowable = BehaviorProcessor.create<Boolean>()
            mPermissionFlowable.put(requestCode, Flowable)
            if (mActivity != null) {
                requestPermission(requestCode, mActivity, *permission)
            } else if (mFragment != null) {
                requestPermission(requestCode, mFragment, *permission)
            }
        }
        return Flowable
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermission(requestCode: Int, from: Activity, vararg permission: String) {
        from.requestPermissions(permission, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermission(requestCode: Int, from: Fragment, vararg permission: String) {
        from.requestPermissions(permission, requestCode)
    }

    fun onRequestPermissionsResult(requestCode: Int, @Suppress("UNUSED_PARAMETER") permissions: Array<String>, grantResults: IntArray) {
        val flowable = mPermissionFlowable.get(requestCode)
        if (flowable != null) {
            mPermissionFlowable.remove(requestCode)
            for (r in grantResults) {
                if (r != PackageManager.PERMISSION_GRANTED) {
                    flowable.onNext(false)
                    return
                }
            }
            flowable.onNext(true)
        }
    }

    companion object {
        private val sAtomicInteger = AtomicInteger(0x888)
    }

}
