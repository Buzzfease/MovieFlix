package top.hanyue.movieflex.content.detail

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_movie_detail.*
import timber.log.Timber
import top.hanyue.movieflex.DataKey
import top.hanyue.movieflex.R
import top.hanyue.movieflex.base.BaseItem
import top.hanyue.movieflex.base.MovieFlexBaseActivity
import top.hanyue.movieflex.content.play.MoviePlayActivity
import top.hanyue.movieflex.network.main.MovieDesc
import top.hanyue.movieflex.network.main.VodPlayUrl
import top.hanyue.movieflex.utils.glide.GlideApi
import top.hanyue.movieflex.widget.SpaceItemDecoration
import top.hanyue.movieflex.widget.TvGridLayoutManagerScrolling
import javax.inject.Inject


class MovieDetailActivity : MovieFlexBaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private var mEpisodeAdapter:EpisodeAdapter? = null
    private val adapterList = ArrayList<BaseItem<Any>>()

    override fun layoutResID(): Int {
        return R.layout.activity_movie_detail
    }

    override fun initViewAndEvent() {
        val vId:String = intent.getStringExtra(DataKey.DATA_KEY_MOVIE_ID)

        val gridlayoutManager: GridLayoutManager = TvGridLayoutManagerScrolling(this, 8)
        gridlayoutManager.orientation = GridLayoutManager.VERTICAL
        reEpisode?.layoutManager = gridlayoutManager
        reEpisode.addItemDecoration(SpaceItemDecoration(60,20))
        reEpisode?.isFocusable = false

        movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.onScreenCreated(vId)
        movieDetailViewModel.getMovieDetailViewState().observe(this, Observer { setViewState(it) })
    }


    private fun setViewState(viewState : MovieDetailViewState){
        when (viewState) {
            is MovieDetailViewState.Loading -> { showLoadingDialog() }
            is MovieDetailViewState.NetworkError -> {
                hideLoadingDialog()
                toast(viewState.message)
            }
            is MovieDetailViewState.Success -> {
                hideLoadingDialog()
                initData(viewState.movieDetail?.data)
            }
        }
    }

    private fun initData(movieDesc: MovieDesc?){
        //电影图片
        GlideApi.get().showCrossFadeImage(ivMoviePic, movieDesc?.vod_pic)
        ivMoviePic.setOnClickListener {
            val playUrl = movieDesc?.vod_play_url!![0].url
            val intent = Intent(this, MoviePlayActivity::class.java)
            intent.putExtra(DataKey.DATA_KEY_PLAY_URL, playUrl)
            startActivity(intent)
        }
        //电影名称
        tvMovieName.text = movieDesc?.vod_name
        //电影类型
        tvMovieType.text = movieDesc?.vod_class
        //电影导演
        tvMovieDirector.text = movieDesc?.vod_director
        //电影演员
        tvMovieActor.text = movieDesc?.vod_actor
        //电影简介
        tvIntro.text = movieDesc?.vod_blurb
        //选集列表
        if (movieDesc?.vod_play_url != null){
            adapterList.clear()
            movieDesc.vod_play_url.forEach {
                adapterList.add(BaseItem(BaseItem.ITEM_EPISODE, it))
            }
            mEpisodeAdapter = EpisodeAdapter()
            mEpisodeAdapter?.setEnableLoadMore(false)
            mEpisodeAdapter?.addData(adapterList)
            mEpisodeAdapter?.setOnItemClickListener { adapter, view, position ->
                Timber.d("position" + position + "点击了")
                val playUrl: VodPlayUrl = mEpisodeAdapter?.getItem(position)?.getData() as VodPlayUrl
                val intent = Intent(this, MoviePlayActivity::class.java)
                intent.putExtra(DataKey.DATA_KEY_PLAY_URL, playUrl.url)
                startActivity(intent)
            }
            reEpisode.adapter = mEpisodeAdapter
        }
        reEpisode.scrollToPosition(0)
    }


    ////////////////////////
    /////   Adapter    /////
    ////////////////////////
    inner class EpisodeAdapter : BaseMultiItemQuickAdapter<BaseItem<Any>, BaseViewHolder>(null) {

        init {
            addItemType(BaseItem.ITEM_EPISODE, R.layout.item_episode)
        }

        override fun convert(helper: BaseViewHolder?, item: BaseItem<Any>?) {
            val playUrl: VodPlayUrl = item?.getData() as VodPlayUrl
            when(helper?.itemViewType) {
                BaseItem.ITEM_EPISODE -> {
                    val tvEpisode = helper.getView<TextView>(R.id.tvEpisode)
                    tvEpisode.text = playUrl.name
                    tvEpisode.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                        v.background = ContextCompat.getDrawable(this@MovieDetailActivity,
                            if (hasFocus) R.drawable.shape_button_focused else R.drawable.shape_button_normal)
                    }
                }
            }
        }
    }
}
