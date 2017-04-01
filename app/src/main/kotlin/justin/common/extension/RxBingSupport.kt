@file:Suppress("unused")

package justin.common.extension

import android.support.annotation.IdRes
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import justin.doteon.R

/**
 * @author justin on 2017/03/31 15:19
 * @version V1.0
 */

//base
fun <T> View.bindFlowable(@IdRes id: Int, creator: ViewFlowableCreator<T>): Flowable<T> {
    val obj: Any? = getTag(id)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Flowable<T>
    }
    val observable = creator.call()
    setTag(id, observable)
    return observable
}


interface ViewFlowableCreator<T> {
    fun call(): Flowable<T>
}

//view base
fun View.click(): Flowable<View> {
    val obj: Any? = getTag(R.id.view_click)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Flowable<View>
    }
    val observable = PublishProcessor.create<View>()
    val listener = View.OnClickListener { v -> observable.onNext(v) }
    setOnClickListener(listener)
    setTag(R.id.view_click, observable)
    return observable
}

fun View.focusChange(): Flowable<Boolean> {
    val obj: Any? = getTag(R.id.view_focus)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Flowable<Boolean>
    }
    val observable = BehaviorProcessor.create<Boolean>()
    val listener = View.OnFocusChangeListener { _, b ->
        observable.onNext(b)
    }
    onFocusChangeListener = listener
    setTag(R.id.view_focus, observable)
    return observable
}


fun View.enable(): Consumer<Boolean> {
    return Consumer { enable -> isEnabled = enable }
}

fun View.visibility(goneOrInvisible: Int): Consumer<Boolean> {
    return Consumer { vis ->
        visibility = if (vis) View.VISIBLE else goneOrInvisible
    }
}

//textView

/**
 * 字符内容
 */
fun TextView.textChange(): Flowable<CharSequence> {
    val creator: ViewFlowableCreator<CharSequence> = object : ViewFlowableCreator<CharSequence> {
        override fun call(): FlowableProcessor<CharSequence> {
            val processor: BehaviorProcessor<CharSequence> = BehaviorProcessor.create()

            addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    processor.onNext(s)
                }
            })
            processor.onNext(text)
            return processor
        }
    }
    return bindFlowable(R.id.textview_length, creator)
}


/**
 * 字符长度
 */
fun TextView.lengthChange(): Flowable<Int> {
    return textChange().map(CharSequence::length)
}

fun TextView.setText(): Consumer<CharSequence> {
    return Consumer { t -> text = t }
}





