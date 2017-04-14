package justin.doteon.guide

import android.app.Activity
import android.os.Bundle
import justin.common.extension.buildIntent
import justin.doteon.main.MainActivity

class GuideActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(buildIntent(MainActivity::class.java))
        finish()
    }
}
