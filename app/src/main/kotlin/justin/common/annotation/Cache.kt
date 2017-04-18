package justin.common.annotation

/**
 * @author justin on 2017/04/13 13:58
 * @version V1.0
 */
/**
 * 单位：秒
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cache(val value: Int)