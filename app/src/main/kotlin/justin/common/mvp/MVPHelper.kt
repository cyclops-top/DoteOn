package mvp

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @author justin on 2016/08/31 15:54
 * *         justin@magicare.me
 * *
 * @version V1.0
 */
internal class MVPHelper<P : BasePresenter<*>>(view: IView) {
    private val mViewAnnotation: BindLayout?
    var presenter: P? = null
        private set

    init {
        @Suppress("UNCHECKED_CAST")
        val pClass = (view.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<P>
        @Suppress("UNCHECKED_CAST")
        try {
            val constructors = pClass.constructors
            presenter = constructors[0].newInstance(view) as P?
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        mViewAnnotation = view.javaClass.getAnnotation(BindLayout::class.java)
    }

    val layout: Int
        get() = mViewAnnotation!!.value
}