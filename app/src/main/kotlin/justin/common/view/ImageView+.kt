package justin.common.view

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author justin on 2017/04/14 09:41
 * @version V1.0
 */


fun ImageView.setImageURI(url:String){
    Glide.with(context).load(url).into(this)
}