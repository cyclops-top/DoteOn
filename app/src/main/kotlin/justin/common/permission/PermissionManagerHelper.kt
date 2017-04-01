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
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author justin on 2017/03/06 09:44
 * *
 * @version V1.0
 */
class PermissionManagerHelper {
    private val mPermissionObservable = SparseArray<BehaviorSubject<Boolean>>()
    private val mActivity: Activity?
    private val mFragment: Fragment?
    private val sAtomicInteger = AtomicInteger(0x888)

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
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    @SuppressLint("NewApi")
    fun requestPermission(vararg permission: String): Observable<Boolean> {
        val isAllow = checkPermission(*permission)
        val observable: BehaviorSubject<Boolean>
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || isAllow) {
            observable = BehaviorSubject.createDefault(isAllow)
        } else {
            val requestCode = sAtomicInteger.getAndIncrement()
            observable = BehaviorSubject.create<Boolean>()
            mPermissionObservable.put(requestCode, observable)
            if (mActivity != null) {
                requestPermission(requestCode, mActivity, *permission)
            } else if (mFragment != null) {
                requestPermission(requestCode, mFragment, *permission)
            }
        }
        return observable
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
        val flowable = mPermissionObservable.get(requestCode)
        if (flowable != null) {
            mPermissionObservable.remove(requestCode)
            for (r in grantResults) {
                if (r != PackageManager.PERMISSION_GRANTED) {
                    flowable.onNext(false)
                    return
                }
            }
            flowable.onNext(true)
        }
    }



}
