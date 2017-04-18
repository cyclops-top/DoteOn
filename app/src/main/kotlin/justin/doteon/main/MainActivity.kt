package justin.doteon.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import justin.common.app.BaseFragmentPagerAdapter
import justin.common.mvp.MVPActivity
import justin.common.utils.bindView
import justin.doteon.R
import justin.doteon.items.ItemsFragment
import justin.doteon.model.MovieType
import mvp.BindLayout


@BindLayout(R.layout.activity_main)
class MainActivity : MVPActivity<MainPresenter>(), IMainView {
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val pager: ViewPager by bindView(R.id.pager)
    private val tabLayout: TabLayout by bindView(R.id.tab_layout)
    override fun bindEvent() {
        super.bindEvent()
        setSupportActionBar(toolbar)
        val adapter = BaseFragmentPagerAdapter(supportFragmentManager)
        var args = Bundle()
//        args.putInt("type", MovieType.IN_THEATERS)
//        adapter.addFragment("正在热映", ItemsFragment::class.java, args)
//        args = Bundle()
//        args.putInt("type", MovieType.COMING)
//        adapter.addFragment("即将上映", ItemsFragment::class.java, args)
//        args = Bundle()
        args.putInt("type", MovieType.TOP250)
        adapter.addFragment("TOP250", ItemsFragment::class.java, args)
        pager.adapter = adapter
        pager.offscreenPageLimit = 5
        tabLayout.setupWithViewPager(pager, false)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_search)//在菜单中找到对应控件的item
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        Log.d("Tag", "menu create")
        MenuItemCompat.setOnActionExpandListener(menuItem, object : MenuItemCompat.OnActionExpandListener {
            //设置打开关闭动作监听
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                Toast.makeText(this@MainActivity, "onExpand", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                Toast.makeText(this@MainActivity, "Collapse", Toast.LENGTH_LONG).show()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}
