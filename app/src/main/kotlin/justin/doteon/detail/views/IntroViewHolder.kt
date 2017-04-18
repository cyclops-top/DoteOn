package justin.doteon.detail.views

import android.view.View
import justin.common.utils.bindView
import justin.common.view.ExpandableTextView
import justin.common.view.MultiTypeViewHolder
import justin.doteon.R
import justin.doteon.model.MovieDetail
import mvp.BindLayout

/**
 * @author justin on 2017/04/15 13:58
 * @version V1.0
 */
@BindLayout(R.layout.detail_items_intro)
class IntroViewHolder(view: View) : MultiTypeViewHolder<MovieDetail>(view) {

    val intro: ExpandableTextView by bindView(R.id.intro)


    override fun bindData(data: MovieDetail?) {
        if (data == null) {
            intro.text = "加载中..."
        }else{
            intro.text = data.summary
        }

    }

}