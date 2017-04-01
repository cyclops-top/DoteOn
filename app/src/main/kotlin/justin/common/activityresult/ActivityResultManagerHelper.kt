@file:Suppress("unused")

package justin.common.activityresult

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.SparseArray
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import justin.common.extension.makeShareViewOptions
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author justin on 2017/04/01 10:36
 * @version V1.0
 */
class ActivityResultManagerHelper : IActivityResultSupport {
    private val mResultObservable = SparseArray<BehaviorSubject<ActivityResult>>()
    private val sAtomicInteger = AtomicInteger(0x999)
    val activity: Activity?
    val fragment: Fragment?

    constructor(activity: Activity) {
        this.activity = activity
        this.fragment = null
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        this.activity = null
    }

    override fun startActivityForResult(intent: Intent): Observable<ActivityResult> {
        val requestCode = sAtomicInteger.getAndIncrement()
        val observable: BehaviorSubject<ActivityResult> = BehaviorSubject.create()
        mResultObservable.put(requestCode, observable)
        activity?.startActivityForResult(intent, requestCode) ?:
                fragment!!.startActivityForResult(intent, requestCode)
        return observable
    }

    override fun startActivityForResult(intent: Intent, vararg shares: View): Observable<ActivityResult> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && shares.isNotEmpty()) {
            val opt: Bundle = activity?.makeShareViewOptions(*shares) ?: fragment!!.makeShareViewOptions(*shares)
            return startActivityForResult(intent, opt)
        } else {
            return startActivityForResult(intent)
        }
    }

    override fun startActivityForResult(intent: Intent, opt: Bundle): Observable<ActivityResult> {
        val requestCode = sAtomicInteger.getAndIncrement()
        val observable: BehaviorSubject<ActivityResult> = BehaviorSubject.create()
        mResultObservable.put(requestCode, observable)
        activity?.startActivityForResult(intent, requestCode, opt) ?:
                fragment!!.startActivityForResult(intent, requestCode, opt)
        return observable
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val observable = mResultObservable.get(requestCode) ?: return
        val result = ActivityResult(resultCode, data)
        observable.onNext(result)
    }

}