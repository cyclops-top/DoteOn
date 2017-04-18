package justin.doteon.model

import java.io.Serializable

/**
 * @author justin on 2017/04/13 14:13
 * *
 * @version V1.0
 */
class MovieList: Serializable {


    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var title: String? = null
    var subjects: List<MovieSubject>? = null

    override fun toString(): String {
        return "MovieList{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}'
    }
}
