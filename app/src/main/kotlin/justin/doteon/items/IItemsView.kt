package justin.doteon.items

import justin.doteon.model.MovieSubject
import mvp.IView

/**
 * @author justin on 2017/04/01 14:47
 * @version V1.0
 */

interface IItemsView:IView{
    fun updateData(data:List<MovieSubject>,isMore: Boolean)
}