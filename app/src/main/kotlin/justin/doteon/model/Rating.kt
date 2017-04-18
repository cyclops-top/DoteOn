package justin.doteon.model

import java.io.Serializable

/**
 * @author justin on 2017/04/13 14:18
 * *
 * @version V1.0
 */
class Rating: Serializable {

    var max: Int = 0
    var average: Double = 0.toDouble()
    var stars: String? = null
    var min: Int = 0

    override fun toString(): String {
        return "Rating{" +
                "max=" + max +
                ", average=" + average +
                ", stars='" + stars + '\'' +
                ", min=" + min +
                '}'
    }
}
