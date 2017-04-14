package justin.doteon.model

/**
 * @author justin on 2017/04/13 14:15
 * *
 * @version V1.0
 */
class MovieSubject {

    var rating: Rating? = null
    var title: String? = null
    var collect_count: Int = 0
    var original_title: String? = null
    var subtype: String? = null
    var year: String? = null
    var images: Images? = null
    var alt: String? = null
    var id: String? = null
    var genres: List<String>? = null
    var casts: List<Cast>? = null
    var directors: List<Director>? = null

    override fun toString(): String {
        return "MovieSubject{" +
                "rating=" + rating +
                ", title='" + title + '\'' +
                ", collect_count=" + collect_count +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", genres=" + genres +
                ", casts=" + casts +
                ", directors=" + directors +
                '}'
    }
}
