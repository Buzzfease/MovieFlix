package top.hanyue.movieflex.content.play

/**
 * Created by Buzz on 2020/4/27.
 * Email :lmx2060918@126.com
 */
sealed class MoviePlayViewState {
    data class NetworkError(val message: String?) : MoviePlayViewState()
    object Loading: MoviePlayViewState()
    data class Success(val fillthis: Object?) : MoviePlayViewState()
}