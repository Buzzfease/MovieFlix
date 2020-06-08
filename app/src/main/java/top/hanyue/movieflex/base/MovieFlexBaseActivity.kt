package top.hanyue.movieflex.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Instrumentation
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import top.hanyue.movieflex.ActivityManager
import top.hanyue.movieflex.R
import top.hanyue.movieflex.utils.DialogFactory
import top.hanyue.movieflex.utils.EasyToast
import javax.inject.Inject

/**
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
@SuppressLint("Registered")
abstract class MovieFlexBaseActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    private var loadingDialog: Dialog? = null

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.pushActivity(this)
        val inst = Instrumentation()
        inst.setInTouchMode(false)
        setContentView(layoutResID())
        initViewAndEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (loadingDialog != null){
            loadingDialog?.dismiss()
        }
        ActivityManager.popActivity(this)
    }

    abstract fun layoutResID(): Int

    abstract fun initViewAndEvent()

    fun showLoadingDialog(str: String?) {
        loadingDialog = DialogFactory.getLoadingDialog(this, str)
    }

    fun showLoadingDialog() {
        loadingDialog = DialogFactory.getLoadingDialog(this, getString(R.string.please_wait))
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing){
            loadingDialog?.dismiss()
        }
    }

    fun toast(content: String?) {
        if (!content.isNullOrEmpty()){
            EasyToast.newBuilder(R.layout.view_toast, R.id.tvToast).build().show(content)
        }
    }
}