package justin.doteon.network

import io.reactivex.Observable
import justin.common.network.Get
import justin.common.network.Path
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
    fun inTheaters(start:Int): Observable<MovieList>

    /**
     * 即将上映
     */
    @Path("/v2/movie/coming_soon?start=%d&count=18")
    @Get
    fun coming(start:Int): Observable<MovieList>
    /**
     * Top250
     */
    @Path("/v2/movie/top250?start=%d&count=18")
    @Get
    fun top250(start:Int): Observable<MovieList>

}