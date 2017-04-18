package justin.common.view

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author justin on 2017/04/14 16:44
 * *
 * @version V1.0
 */
abstract class MultiTypeViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private var data: MultiTypeRecyclerAdapter.ItemDataBase<Any, T>? = null
    var onItemClickListener:MultiTypeRecyclerAdapter.OnItemClickListener<T>? = null
    var offset:Int? = null
    init {
        itemView.setOnClickListener { v ->
            data!!.mOnItemClickListener?.onItemClick(v,data!!.getData(offset!!))
        }
        @Suppress("LeakingThis")
        bindViews()
    }

    fun findViewById(@IdRes id: Int): View {
        return itemView.findViewById(id)
    }
    fun setItemData(d:MultiTypeRecyclerAdapter.ItemDataBase<Any,T>,offset:Int){
        data = d
        this.offset = offset
        val itemData = data!!.getData(offset)
        bindData(itemData)
    }

    open fun bindViews() {}
    abstract fun bindData(data: T?)

}
