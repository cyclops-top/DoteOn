package justin.common.app

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrDefaultHandler2
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler2
import `in`.srain.cube.views.ptr.header.StoreHouseHeader
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import justin.doteon.R

/**
 * @author justin on 2017/04/13 09:14
 * @version V1.0
 */

abstract class LoadFragment : BaseFragment(), PtrHandler2 {
    var ptrLayout: PtrFrameLayout? = null
    var content: View? = null
    var isLoading: Boolean? = false
    override fun viewCreate(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ptrLayout = inflater!!.inflate(R.layout.fragment_load, container, false) as PtrFrameLayout
        val contentLayout = ptrLayout!!.findViewById(R.id.content) as FrameLayout
        content = inflater.inflate(getLayout(), contentLayout, false)
        contentLayout.addView(content!!, 0)
        content = content!!.findViewById(getScrollViewId())
        ptrLayout!!.setPtrHandler(this)
        ptrLayout!!.mode = PtrFrameLayout.Mode.BOTH
        initHeader()
        initFooter()
        ptrLayout!!.setDurationToCloseHeader(400)
        ptrLayout!!.setDurationToCloseFooter(400)
        return ptrLayout
    }


    override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, this.content!!, header)
    }

    override fun checkCanDoLoadMore(frame: PtrFrameLayout, content: View, footer: View): Boolean {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, this.content!!, footer)
    }

    fun loadComplete() {
        ptrLayout!!.refreshComplete()
        isLoading = false
    }

    override fun onLoadMoreBegin(frame: PtrFrameLayout) {
        loadMoreBegin()
        isLoading = true
    }

    override fun onRefreshBegin(frame: PtrFrameLayout) {
        refreshBegin()
        isLoading = true
    }

    private fun initHeader() {
        val header = StoreHouseHeader(context)
        header.setBackgroundColor(Color.TRANSPARENT)
        header.initWithString("LOADING", 20)

        header.setLineWidth(3)
        ptrLayout!!.headerView = header
        ptrLayout!!.addPtrUIHandler(header)
    }

    private fun initFooter() {
        val footer = StoreHouseHeader(context)
        footer.setBackgroundColor(Color.TRANSPARENT)
        footer.initWithString("MORE", 20)

        footer.setLineWidth(3)
        ptrLayout!!.setFooterView(footer)
        ptrLayout!!.addPtrUIHandler(footer)
    }

    fun isLoading(): Boolean {
        return isLoading!!
    }

    abstract fun loadMoreBegin()

    abstract fun refreshBegin()

    @IdRes
    abstract fun getScrollViewId(): Int


}