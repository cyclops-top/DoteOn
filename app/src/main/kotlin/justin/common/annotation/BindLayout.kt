package mvp

import android.support.annotation.LayoutRes


/**
 * @author justin on 2016/12/31 22:54
 * @version V1.0
 */

@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindLayout(@LayoutRes val value:Int)