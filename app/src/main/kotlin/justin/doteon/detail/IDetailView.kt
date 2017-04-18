package justin.doteon.detail

import justin.doteon.model.MovieDetail
import mvp.IView

/**
 * @author justin on 2017/04/14 10:58
 * @version V1.0
 */
interface IDetailView: IView{
    fun showDetail(detail:MovieDetail?)
}