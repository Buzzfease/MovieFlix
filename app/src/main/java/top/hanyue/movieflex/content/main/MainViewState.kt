package top.hanyue.movieflex.content.main

import top.hanyue.movieflex.network.main.Navigation

/**
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
sealed class MainViewState{
    data class NetworkError(val message: String?) : MainViewState()
    object Loading: MainViewState()
    data class Success(val navigation: Navigation?) : MainViewState()
}

