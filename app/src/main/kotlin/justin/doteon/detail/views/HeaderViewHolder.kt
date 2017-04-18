package justin.doteon.detail.views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import justin.common.extension.setImageURL
import justin.common.utils.bindView
import justin.common.view.MultiTypeViewHolder
import justin.doteon.R
import justin.doteon.model.MovieSubject
import mvp.BindLayout

/**
 * @author justin on 2017/04/14 17:18
 * @version V1.0
 */
@BindLayout(R.layout.detail_items_header)
class HeaderViewHolder(view: View): MultiTypeViewHolder<MovieSubject>(view) {

    val poster: ImageView by bindView(R.id.poster)
    val name: TextView by bindView(R.id.name)
    val rating: TextView by bindView(R.id.rating)
    val aka: TextView by bindView(R.id.aka)
    val genres: LinearLayout by bindView(R.id.genres)

    @SuppressLint("SetTextI18n")
    override fun bindData(data: MovieSubject?) {

        val large = data!!.images?.large

        if (large != null){
            poster.setImageURL(large)
        }
        name.text = data.title

        if(!data.title.equals(data.original_title)){
            aka.text = "原名：${data.original_title}"
            aka.visibility = VISIBLE
        }else{
            aka.visibility = GONE
        }
        var info = "年代：${data.year}"
        val average = data.rating?.average
        if(average != null && average>0){
            info += "   评分：$average"
        }
        rating.text = info
        if(data.genres != null){
            val inflater = LayoutInflater.from(genres.context)
            val max = Math.min(data.genres!!.size,5) - 1
            for ( i in 0..max){
                val textView = inflater.inflate(R.layout.genres_text_layout,genres,false) as TextView
                genres.addView(textView)
                textView.text = data.genres!![i]
            }
        }
    }

}