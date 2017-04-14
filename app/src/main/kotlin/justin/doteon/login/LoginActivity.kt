package justin.doteon.login

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import justin.common.extension.click
import justin.common.mvp.MVPActivity
import justin.common.utils.bindView
import justin.doteon.R
import mvp.BindLayout

@BindLayout(R.layout.activity_login)
class LoginActivity : MVPActivity<LoginPresenter>(), ILoginView {


    val signButton: Button by bindView(R.id.sign_in_button)
    val usernameView: EditText by bindView(R.id.user)
    val passwordView: EditText by bindView(R.id.password)

    override fun bindEvent() {
        super.bindEvent()
        signButton.click().subscribe { _ ->
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        usernameView.error = null
        passwordView.error = null

        val email = usernameView.getText().toString()
        val password = passwordView.getText().toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(password)) {
            passwordView.error = getString(R.string.error_invalid_password)
            focusView = passwordView
            cancel = true
        }

        if (TextUtils.isEmpty(email)) {
            usernameView.error = getString(R.string.error_field_required)
            focusView = usernameView
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            showProgress(true)
//            presenter.login(email, password).subscribe { isSuccess ->
//                dismissProgress()
//                if (isSuccess) {
//                    toast("登录成功")
//                    startActivity(buildIntent(MainActivity::class.java))
//                    finish()
//                } else {
//                    toast("登录失败")
//                }
//            }
        }
    }
}
