package justin.doteon.detail

import justin.common.extension.bindLife
import justin.common.network.Api
import justin.doteon.network.DouBanApis
import mvp.BasePresenter

/**
 * @author justin on 2017/04/14 10:58
 * @version V1.0
 */
class DetailPresenter(view:IDetailView):BasePresenter<IDetailView>(view){
    fun getDetail(id:String){
        Api.get(DouBanApis::class.java).detail(id)
                .bindLife(this)
                .subscribe(view::showDetail,{ _ ->
                    view.showDetail(null)
                })
    }
}