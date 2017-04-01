package justin.doteon

import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.SparseArray
import android.widget.TextView
import justin.common.mvp.MVPActivity
import justin.common.utils.bindView
import justin.doteon.items.ItemsFragment
import mvp.BindLayout

@BindLayout(R.layout.activity_main)
class MainActivity : MVPActivity<MainPresenter>(), IMainView {

    private val mTextMessage: TextView by bindView(R.id.message)
    private val mNavigation: BottomNavigationView by bindView(R.id.navigation)
    private var pagerManager: PagerManager? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage.setText(R.string.title_home)
                pagerManager!!.show(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun bindEvent() {
        super.bindEvent()
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        pagerManager = PagerManager(supportFragmentManager, R.id.content)
        pagerManager!!.add(0, ItemsFragment::class.java)
    }


    class PagerManager(val manager: FragmentManager, @IdRes val containerViewId: Int) {
        val fragmentClass: SparseArray<Class<out Fragment>> = SparseArray()
        var currFragment: Fragment? = null

        fun add(tag: Int, clazz: Class<out Fragment>) {
            fragmentClass.put(tag, clazz)
        }

        fun show(tag: Int) {
            val transaction = manager.beginTransaction()
            val clazz = fragmentClass.get(tag)!!
            val key = clazz.name+tag
            var nextFragment: Fragment? = manager.findFragmentByTag(key)
            if (nextFragment == null){
                nextFragment = clazz.newInstance()
                transaction.add(containerViewId, nextFragment)
            }
            if(currFragment == nextFragment){
                return
            }
            if(currFragment != null){
                transaction.hide(currFragment)
            }
            currFragment = nextFragment
            transaction.show(currFragment)
            transaction.commitAllowingStateLoss()
        }


    }
}
