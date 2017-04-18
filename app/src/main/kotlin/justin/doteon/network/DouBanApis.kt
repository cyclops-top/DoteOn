package justin.doteon.network

import io.reactivex.Observable
import justin.common.annotation.Cache
import justin.common.annotation.Get
import justin.common.annotation.Path
import justin.doteon.model.MovieDetail
import justin.doteon.model.MovieList

/**
 * @author justin on 2017/04/13 14:11
 * @version V1.0
 */

interface DouBanApis{
    /**
     * 正在热映
     */
    @Path("/v2/movie/in_theaters?start=%d&count=18")
    @Get
    @Cache(60*60)
    fun inTheaters(start:Int): Observable<MovieList>

    /**
     * 即将上映
     */
    @Path("/v2/movie/coming_soon?start=%d&count=18")
    @Get
    @Cache(60*60)
    fun coming(start:Int): Observable<MovieList>
    /**
     * Top250
     */
    @Path("/v2/movie/top250?start=%d&count=18")
    @Get
    @Cache(60*60)
    fun top250(start:Int): Observable<MovieList>

    @Path("/v2/movie/subject/%s")
    @Get
    @Cache(60*60)
    fun detail(id:String):Observable<MovieDetail>
}