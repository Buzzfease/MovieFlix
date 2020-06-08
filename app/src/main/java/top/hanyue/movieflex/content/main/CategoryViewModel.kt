package top.hanyue.movieflex.content.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import top.hanyue.movieflex.network.MovieFlexApi
import javax.inject.Inject

/**
 * Created by Buzz on 2020/4/16.
 * Email :lmx2060918@126.com
 */
class CategoryViewModel @Inject constructor(
    private val movieFlexApi: MovieFlexApi
) : ViewModel() {

    private val categoryLiveData: MutableLiveData<CategoryViewState> = MutableLiveData()

    fun getRecommendViewState() : LiveData<CategoryViewState> {
        return categoryLiveData
    }

    //获取推荐
    fun getRecommendList(module: String){
        categoryLiveData.value = CategoryViewState.Loading
        viewModelScope.launch(recommendExceptionHandler) {
            val response = movieFlexApi.getRecommendList(module, "1", "10")
            categoryLiveData.value = CategoryViewState.Success(response)
        }
    }

    private val recommendExceptionHandler = CoroutineExceptionHandler { _, exception ->
        categoryLiveData.value = CategoryViewState.NetworkError(exception.message)
        Timber.e(exception)
    }
}