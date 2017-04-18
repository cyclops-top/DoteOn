package justin.doteon.detail

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import justin.common.extension.setImageBlurURL
import justin.common.mvp.MVPActivity
import justin.common.utils.bindView
import justin.common.view.MultiTypeRecyclerAdapter
import justin.doteon.R
import justin.doteon.detail.views.HeaderViewHolder
import justin.doteon.detail.views.IntroViewHolder
import justin.doteon.detail.views.ListTestViewHolder
import justin.doteon.model.MovieDetail
import justin.doteon.model.MovieSubject
import mvp.BindLayout


/**
 * @author justin on 2017/04/14 10:57
 * @version V1.0
 */
@BindLayout(R.layout.activity_detail)
class DetailActivity:MVPActivity<DetailPresenter>(),IDetailView{


    var movieData:MovieSubject? = null
    val list:RecyclerView by bindView(R.id.list)
    val posterBg: ImageView by bindView(R.id.poster_bg)
    var intro:MultiTypeRecyclerAdapter.ItemData<MovieDetail>? = null

    @SuppressLint("SetTextI18n")
    override fun bindEvent() {
        super.bindEvent()
        movieData = intent?.getSerializableExtra("data") as MovieSubject
        val medium = movieData!!.images?.medium
        if(medium != null) {
            posterBg.setImageBlurURL(medium)
        }
        list.itemAnimator = null
        val adapter= MultiTypeRecyclerAdapter()
        adapter.add(HeaderViewHolder::class.java,movieData!!)
        intro = adapter.add(IntroViewHolder::class.java,null as MovieDetail?)

        val test = ArrayList<Int>()
        test.add(1)
        test.add(2)
        test.add(3)
        test.add(4)
        val test2 = adapter.addFromList(ListTestViewHolder::class.java,test)
        list.adapter = adapter
        presenter.getDetail(movieData!!.id!!)
        test.add(8)
        test2.update(test)
        test2.setOnItemClickListener { _, _ ->
            test.removeAt(0)
            test2.update(test)
        }
    }
    override fun showDetail(detail: MovieDetail?) {
        intro!!.update(detail)
    }
}