package justin.common.network

/**
 * @author justin on 2017/04/13 13:58
 * @version V1.0
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value: String)