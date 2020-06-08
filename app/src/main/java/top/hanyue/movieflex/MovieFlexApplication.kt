package top.hanyue.movieflex

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import top.hanyue.movieflex.di.AppComponent
import top.hanyue.movieflex.di.AppInjector
import top.hanyue.movieflex.di.DaggerAppComponent
import top.hanyue.movieflex.utils.glide.GlideApi
import top.hanyue.movieflex.utils.glide.GlideModule
import javax.inject.Inject

/**
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
class MovieFlexApplication: MultiDexApplication(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    lateinit var appComponent: AppComponent

    companion object{
        private var instance: MovieFlexApplication? = null
        fun  instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .glideModule(GlideModule())
            .build()
        appComponent.inject(this)
        AppInjector.init(this)

        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
            //使用使用IjkPlayer解码
            .setPlayerFactory(IjkPlayerFactory.create())
//            .setPlayerFactory(AndroidMediaPlayerFactory.create())
            .build());
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}