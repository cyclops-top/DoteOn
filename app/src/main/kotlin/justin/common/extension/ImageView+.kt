package justin.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * @author justin on 2017/04/14 09:41
 * @version V1.0
 */


fun ImageView.setImageURL(url:String){
    Glide.with(context).load(url).into(this)
}

fun ImageView.setImageBlurURL(url:String){
    Glide.with(context).load(url)
            .bitmapTransform(BlurTransformation(context,4))
            .into(this)
}