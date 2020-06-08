package top.hanyue.movieflex.utils.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.MainThread
import com.bumptech.glide.RequestBuilder
import top.hanyue.movieflex.MovieFlexApplication
import java.io.File
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Buzz on 2020/4/16.
 * Email :lmx2060918@126.com
 */
class GlideApi{

    companion object {
        private var mInstance: GlideApi? = null
        @MainThread
        fun get(): GlideApi {
            if (mInstance == null) {
                mInstance = GlideApi()
            }
            return mInstance!!
        }
    }

    init {
        MovieFlexApplication.instance().appComponent.inject(this)
    }

    @Inject
    @field:Named("default")
    lateinit var defaultBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircle")
    lateinit var circleBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCrossFade")
    lateinit var crossFadeBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircleCrossFade")
    lateinit var circleCrossFadeBuilder: RequestBuilder<Drawable>


    fun showImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            defaultBuilder.load(url).into(imageView)
        }
    }


    fun showCircleImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleBuilder.load(url).into(imageView)
        }
    }


    fun showCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            crossFadeBuilder.load(url).into(imageView)
        }
    }


    fun showCircleCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleCrossFadeBuilder.load(url).into(imageView)
        }
    }


    private fun handleUrls(url:Any):Any?{
        if (url is String || url is Int || url is File){
            if (url is String) {
                if (url.endsWith("null")) {
                    return null
                }
            }
        }
        return url
    }
}