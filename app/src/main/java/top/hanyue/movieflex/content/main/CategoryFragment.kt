package top.hanyue.movieflex.content.main

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_category.*
import timber.log.Timber
import top.hanyue.movieflex.DataKey
import top.hanyue.movieflex.R
import top.hanyue.movieflex.base.BaseItem
import top.hanyue.movieflex.base.MovieFlexBaseFragment
import top.hanyue.movieflex.content.detail.MovieDetailActivity
import top.hanyue.movieflex.di.Injectable
import top.hanyue.movieflex.network.main.Movie
import top.hanyue.movieflex.network.main.RecommendPage
import top.hanyue.movieflex.utils.glide.GlideApi
import top.hanyue.movieflex.widget.BorderView
import top.hanyue.movieflex.widget.TvGridLayoutManagerScrolling
import javax.inject.Inject


/**
 * Created by Buzz on 2020/4/20.
 * Email :lmx2060918@126.com
 */
class CategoryFragment :MovieFlexBaseFragment(),Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var categoryViewModel: CategoryViewModel

    private var fragmentId:Int? = 0
    private var columnName:String? = null
    private val adapterList = ArrayList<BaseItem<Any>>()


    override fun layoutResID(): Int {
        return R.layout.fragment_category
    }

    override fun initEventView() {
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)

        //接收的值
        if (arguments != null){
            fragmentId = arguments?.getInt("id")
            columnName = arguments?.getString("column_name")
            Timber.d("接收到了传值%s", fragmentId)

            categoryViewModel.getRecommendList(fragmentId.toString())
            categoryViewModel.getRecommendViewState().observe(this, Observer { setViewState(it) })

        }
    }

    private fun setViewState(categoryViewState: CategoryViewState) {
        when (categoryViewState) {
            is CategoryViewState.Loading -> {  }
            is CategoryViewState.NetworkError -> {
                //toast(recommendViewState.message)
            }
            is CategoryViewState.Success -> {
                showData(categoryViewState.recommend?.data)
            }
        }
    }

    private fun showData(recommendPage: RecommendPage?){
        adapterList.clear()
        recommendPage?.list?.forEach {
            it.list.forEach { movie ->
                adapterList.add(BaseItem(BaseItem.ITEM_MOVIE, movie))
            }
        }
        addMovieRow()
    }

    private fun addMovieRow(){
        val gridlayoutManager: GridLayoutManager = TvGridLayoutManagerScrolling(requireContext(), 4)
        gridlayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView?.layoutManager = gridlayoutManager
        recyclerView?.isFocusable = false

        //基本用法
        val border = BorderView<View>(requireContext())
        //border.setBackgroundResource(R.drawable.border_highlight)
        border.attachTo(recyclerView)

        val adapter = MovieAdapter()
        adapter.setEnableLoadMore(false)
        adapter.setOnItemClickListener { _, _, position ->

            Timber.d("position" + position + "点击了")
            val movie: Movie = adapter.getItem(position)?.getData() as Movie
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(DataKey.DATA_KEY_MOVIE_ID, movie.vod_id.toString())
            startActivity(intent)
        }
        adapter.addData(adapterList)
        recyclerView?.adapter = adapter
    }



    inner class MovieAdapter : BaseMultiItemQuickAdapter<BaseItem<Any>, BaseViewHolder>(null) {

        init {
            addItemType(BaseItem.ITEM_MOVIE, R.layout.item_movie)
        }

        override fun convert(helper: BaseViewHolder?, item: BaseItem<Any>?) {
            val movie: Movie = item?.getData() as Movie
            when(helper?.itemViewType) {
                BaseItem.ITEM_MOVIE -> {
                    val ivBg = helper.getView<ImageView>(R.id.ivBg)
                    val tvName = helper.getView<TextView>(R.id.tvName)
                    tvName.text = movie.vod_name
                    GlideApi.get().showCrossFadeImage(ivBg, movie.vod_pic)
                }
            }
        }
    }
}