package justin.common.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * @author justin on 2017/04/12 17:05
 * @version V1.0
 */

class BaseFragmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mPagerData = ArrayList<PagerItem>()

    fun addFragment(title: String, clz: Class<out Fragment>) {
        addFragment(title,clz,null)
    }
    fun addFragment(title: String, clz: Class<out Fragment>, args: Bundle?) {

        val pagerItem = PagerItem(title, clz, args)
        mPagerData.add(pagerItem)
    }
    fun clear() {
        mPagerData.clear()
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        try {
            fragment = mPagerData[position].fragmentClass.newInstance()
            fragment!!.arguments = mPagerData[position].args
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return fragment!!
    }


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getCount(): Int {
        return mPagerData.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mPagerData[position].title
    }

    private class PagerItem {
        val title: String
        val fragmentClass: Class<out Fragment>
        var args: Bundle?

        constructor(title: String, fragmentClass: Class<out Fragment>, args: Bundle?) {
            this.title = title
            this.fragmentClass = fragmentClass
            this.args = args
        }

    }

}