package justin.doteon.items

import android.content.Intent
import android.support.v7.widget.RecyclerView
import justin.common.app.SharedElementHelper
import justin.common.utils.bindView
import justin.doteon.R
import justin.doteon.detail.DetailActivity
import justin.doteon.model.MovieSubject
import mvp.BindLayout
import mvp.MVPLoadFragment

/**
 * @author justin on 2017/04/01 14:46
 * @version V1.0
 */
@BindLayout(R.layout.fragment_items)
class ItemsFragment: MVPLoadFragment<ItemsPresenter>(),IItemsView{
    val list: RecyclerView by bindView(R.id.list)
    val adapter = ItemsAdapter()
    val data: ArrayList<MovieSubject> = ArrayList()
    var isLoaded = false

    override fun bindEvent() {
        super.bindEvent()
        presenter.type = arguments["type"]!! as Int
        adapter.list = data
        list.adapter = adapter
        adapter.setOnItemClickListener { v, position ->
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("data",data[position])
            startActivity(intent, SharedElementHelper.makeSceneTransitionAnimation(activity))
        }
    }
    override fun updateData(data: List<MovieSubject>,isMore: Boolean) {
        val size = this.data.size
        val newSize = data.size
        if(!isMore) {
            this.data.clear()
        }
        this.data.addAll(data)
        loadComplete()
        if(!isMore) {
            adapter.notifyItemRangeRemoved(0, size)
            adapter.notifyItemRangeInserted(0,newSize)
        }else{
            adapter.notifyItemRangeInserted(size,newSize)
        }
    }


    override fun refreshBegin() {
        presenter.refresh()
    }

    override fun loadMoreBegin() {
        presenter.loadMore(data)
    }

    override fun getScrollViewId(): Int {
        return R.id.list
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if(!isLoaded && !isLoading()) {
            isLoaded = true
            presenter.refresh()
        }
    }
}