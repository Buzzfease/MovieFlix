package top.hanyue.movieflex.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import top.hanyue.movieflex.R
import top.hanyue.movieflex.utils.DialogFactory
import top.hanyue.movieflex.utils.EasyToast
import javax.inject.Inject

/**
 * Created by Buzz on 2020/4/15.
 * Email :lmx2060918@126.com
 */
abstract class MovieFlexBaseFragment : Fragment(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    private var loadingDialog: Dialog? = null
    private lateinit var mView: View


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutResID() != 0) {
            mView = inflater.inflate(layoutResID(), container, false)
            mView
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventView()
    }

    abstract fun initEventView()

    abstract fun layoutResID(): Int

    fun showLoadingDialog(str: String?) {
        loadingDialog = DialogFactory.getLoadingDialog(activity!!, str)
    }

    fun showLoadingDialog() {
        loadingDialog = DialogFactory.getLoadingDialog(activity!!, getString(R.string.please_wait))
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