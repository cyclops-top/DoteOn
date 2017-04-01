@file:Suppress("unused")

package justin.common.extension

import android.support.annotation.IdRes
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import justin.doteon.R

/**
 * @author justin on 2017/03/31 15:19
 * @version V1.0
 */

//base
fun <T> View.bindObservable(@IdRes id: Int, creator: ViewObservableCreator<T>): Observable<T> {
    val obj: Any? = getTag(id)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Observable<T>
    }
    val observable = creator.call()
    setTag(id, observable)
    return observable
}


interface ViewObservableCreator<T> {
    fun call(): Observable<T>
}

//view base
fun View.click(): Observable<View> {
    val obj: Any? = getTag(R.id.view_click)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Observable<View>
    }
    val observable = PublishSubject.create<View>()
    val listener = View.OnClickListener { v -> observable.onNext(v) }
    setOnClickListener(listener)
    setTag(R.id.view_click, observable)
    return observable
}

fun View.focusChange(): Observable<Boolean> {
    val obj: Any? = getTag(R.id.view_focus)
    if (obj != null) {
        @Suppress("UNCHECKED_CAST")
        return obj as Observable<Boolean>
    }
    val observable = PublishSubject.create<Boolean>()
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
fun TextView.textChange(): Observable<CharSequence> {
    val creator: ViewObservableCreator<CharSequence> = object : ViewObservableCreator<CharSequence> {
        override fun call(): Observable<CharSequence> {
            val processor: PublishSubject<CharSequence> = PublishSubject.create()

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
    return bindObservable(R.id.textview_length, creator)
}


/**
 * 字符长度
 */
fun TextView.lengthChange(): Observable<Int> {
    return textChange().map(CharSequence::length)
}

fun TextView.setText(): Consumer<CharSequence> {
    return Consumer { t -> text = t }
}





