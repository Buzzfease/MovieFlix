package top.hanyue.movieflex.content.main

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import top.hanyue.movieflex.R
import top.hanyue.movieflex.base.MovieFlexBaseActivity
import top.hanyue.movieflex.network.main.Navigation
import top.hanyue.movieflex.widget.SpaceItemDecoration
import javax.inject.Inject


class MainActivity : MovieFlexBaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel
    private var mTitleAdapter:TitleAdapter? = null
    private var mFragAdapter: FragAdapter? = null
    var mTitleList: ArrayList<TitleBean> = ArrayList()
    var mFragmentList: ArrayList<Fragment> = ArrayList()


    override fun layoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {
        //EasyStatusBar.makeStatusBarTransparent(this, true, rlMain, mNavigationLinearLayout)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.onScreenCreated()
        mainViewModel.getHomeViewState().observe(this, Observer { setViewState(it) })

    }

    private fun setViewState(viewState: MainViewState){
        when (viewState) {
            is MainViewState.Loading -> { showLoadingDialog() }
            is MainViewState.NetworkError -> {
                hideLoadingDialog()
                toast(viewState.message)
            }
            is MainViewState.Success -> {
                hideLoadingDialog()
                initData(viewState.navigation)
            }
        }
    }


    private fun initData(navigation: Navigation?) {
        mTitleList.clear()
        mFragmentList.clear()
        navigation?.data?.forEachIndexed { index, column ->
            //add title
            mTitleList.add(TitleBean(index.toString(), column.column_name))

            //add Fragment
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putInt("id", column.id)
            bundle.putString("column_name", column.column_name)
            fragment.arguments = bundle
            mFragmentList.add(fragment)

        }
        initTitleRecyclerView()
        initContentViews()
        mFragAdapter!!.notifyDataSetChanged()
    }

    private fun initTitleRecyclerView() {
        mTitleAdapter = TitleAdapter()
        titleGrid.setPadding(60, 0, 0, 0)
        titleGrid.addItemDecoration(SpaceItemDecoration(20, 20))
        titleGrid.adapter = mTitleAdapter
        titleGrid.setOnChildSelectedListener { parent, view, position, id ->
            // 保持选中的颜色.
            val tv = view.findViewById<TextView>(R.id.title_tv)
            tv.setTextColor(ContextCompat.getColor(this, R.color.title_select_color))
            // 翻页.
            val index = view.tag as Int
            if (index >= 0) {
                viewPager.currentItem = index
            }
        }
        titleGrid.setOnKeyInterceptListener { event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DPAD_UP) { // BUG:避免焦点跑到搜索框去.
                searchBtn.isFocusableInTouchMode = true
                searchBtn.isFocusable = true
                searchBtn.requestFocusFromTouch()
            }
            false
        }
    }

    private fun initContentViews() {
        mFragAdapter = FragAdapter(supportFragmentManager)
        searchBtn.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            searchBtn.setBackgroundResource(if (b) R.drawable.trailer_btn_focused else R.drawable.trailer_btn_normal)
        }
        searchBtn.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                searchBtn.isFocusable = false
                titleGrid.requestFocusFromTouch()
                return@OnKeyListener true
            }
            false
        })
        searchBtn.setOnClickListener {
            //:TODO:Search
        }
        viewPager.offscreenPageLimit = 4 // 缓存3个页面
        viewPager.adapter = mFragAdapter
    }

    ////////////////////////
    /////   Adapter    /////
    ////////////////////////
    /**
     * 标题栏 Adapter.
     */
    inner class TitleAdapter : RecyclerView.Adapter<TitleAdapter.ViewHolder>() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view =
                View.inflate(parent.context, R.layout.item_title_layout, null)
            view.isDuplicateParentStateEnabled = true
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.tag = position
            holder.nameTv.text = mTitleList[position].name
            holder.itemView.onFocusChangeListener =
                View.OnFocusChangeListener { view, b ->
                    // TODO xml 设置 android:duplicateParentState="true" selector 无效，临时这样处理.
                    val tv = view.findViewById<TextView>(R.id.title_tv)
                    val lineView = view.findViewById<View>(R.id.title_line_view)
                    lineView.setBackgroundColor(ContextCompat.getColor(this@MainActivity, if (b) R.color.title_select_color else R.color.clear_color))
                    tv.setTextColor(ContextCompat.getColor(this@MainActivity, if (b) R.color.title_select_color else R.color.title_none_color))
                    // 焦点已不再.
                    if (view.tag as Int != viewPager.currentItem) {
                        tv.setTextColor(ContextCompat.getColor(this@MainActivity, if (b) R.color.title_select_color else R.color.title_none_color))
                    }
                }
        }

        override fun getItemCount(): Int {
            return if (null != mTitleList) mTitleList.size else 0
        }

        inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
            var nameTv: TextView = view.findViewById(R.id.title_tv)
        }
    }

    ////////////////////////
    /////   Adapter    /////
    ////////////////////////
    /**
     * 内容 fragment Adapter.
     */
    inner class FragAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }
    }

    //////////////////////
    /////    bean    /////
    //////////////////////
    data class TitleBean constructor(val id:String, val name:String)

}
