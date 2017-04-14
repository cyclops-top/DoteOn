package justin.doteon.items

import io.reactivex.Observable
import justin.common.extension.bindLife
import justin.common.extension.logd
import justin.common.network.Api
import justin.doteon.model.MovieList
import justin.doteon.model.MovieSubject
import justin.doteon.model.MovieType
import justin.doteon.network.DouBanApis
import mvp.BasePresenter

/**
 * @author justin on 2017/04/01 14:47
 * @version V1.0
 */

class ItemsPresenter(view: IItemsView) : BasePresenter<IItemsView>(view) {

    var type:Int? = MovieType.IN_THEATERS


    fun refresh() {
        load(0).subscribe { data ->
            view.updateData(data,false)
        }
    }

    fun loadMore(history: ArrayList<MovieSubject>?) {
        val start = history?.size ?: 0
        load(start).subscribe { data ->
            view.updateData(data,true)
        }
    }


    private fun load(start: Int): Observable<List<MovieSubject>> {
        return getApi(start).bindLife(this).onErrorReturn { e ->
            logd("justin" + e.message!!)
            MovieList()
        }.map { movieList ->
            if (movieList.count == 0) {
                return@map ArrayList<MovieSubject>()
            } else {
                return@map movieList.subjects
            }
        }
    }

    private fun getApi(start: Int):Observable<MovieList>{
        if(type!! == MovieType.IN_THEATERS) {
            return  Api.get(DouBanApis::class.java).inTheaters(start)
        }else if (type == MovieType.COMING){
            return Api.get(DouBanApis::class.java).coming(start)
        }else if (type == MovieType.TOP250){
            return Api.get(DouBanApis::class.java).top250(start)
        }
        throw RuntimeException("$type is not support")
    }

}