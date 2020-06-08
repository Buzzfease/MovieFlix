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
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
class MainViewModel @Inject constructor(
    private val movieFlexApi: MovieFlexApi
) : ViewModel() {

    private val mainLiveData: MutableLiveData<MainViewState> = MutableLiveData()


    fun getHomeViewState(): LiveData<MainViewState> {
        return mainLiveData
    }


    //获取导航
    fun onScreenCreated(){
        mainLiveData.value = MainViewState.Loading

        //viewModelScope在主分发器内部开启新协程
        viewModelScope.launch(navigationExceptionHandler) {

            //suspend 方法在retrofit中会在IO分发器中开启新协程
            val response = movieFlexApi.getNavigationColumn()

            //在主分发器中返回结果
            mainLiveData.value = MainViewState.Success(response)
        }
    }


    private val navigationExceptionHandler = CoroutineExceptionHandler { _, exception ->
        mainLiveData.value = MainViewState.NetworkError(exception.message)
        Timber.e(exception)
    }
}