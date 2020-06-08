package top.hanyue.movieflex.content.main

import top.hanyue.movieflex.network.main.Recommend

/**
 * Created by Buzz on 2020/4/15.
 * Email :lmx2060918@126.com
 */
sealed class CategoryViewState{
    data class NetworkError(val message: String?) : CategoryViewState()
    object Loading: CategoryViewState()
    data class Success(val recommend: Recommend?) : CategoryViewState()
}
