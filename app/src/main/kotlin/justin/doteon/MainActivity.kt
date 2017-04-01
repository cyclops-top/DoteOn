package justin.doteon

import android.Manifest
import android.support.design.widget.BottomNavigationView
import android.widget.TextView
import justin.common.extension.loge
import justin.common.mvp.MVPActivity
import justin.common.utils.bindView
import mvp.BindLayout

@BindLayout(R.layout.activity_main)
class MainActivity : MVPActivity<MainPresenter>(),IMainView {

    private val mTextMessage:TextView by bindView(R.id.message)
    private val mNavigation:BottomNavigationView by bindView(R.id.navigation)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage.setText(R.string.title_home)

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
//        mTextMessage.lengthChange().map(Int::toString).subscribe(this::toast)
        requestPermission(Manifest.permission.READ_SMS).subscribe { result->
            run {
                loge(result.toString())
            }
        }
    }


}
