package justin.doteon.model

import java.io.Serializable

/**
 * @author justin on 2017/04/13 14:15
 * *
 * @version V1.0
 */
class Images: Serializable {
    var small: String? = null
    var large: String? = null
    var medium: String? = null

    override fun toString(): String {
        return "Images{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                '}'
    }
}
