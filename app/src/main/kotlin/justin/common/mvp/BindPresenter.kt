package mvp

import kotlin.reflect.KClass

/**
 * @author justin on 2017/01/01 02:05
 * @version V1.0
 */

@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindPresenter(val value: KClass<*>)