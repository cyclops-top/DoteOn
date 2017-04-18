package justin.doteon

import android.app.Application
import justin.common.network.Client

/**
 * @author justin on 2017/04/08 12:01
 * *
 * @version V1.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Client.init(this)
    }

}
