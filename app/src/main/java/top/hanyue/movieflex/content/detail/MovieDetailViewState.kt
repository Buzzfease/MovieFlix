package top.hanyue.movieflex.content.detail

import top.hanyue.movieflex.network.main.MovieDetail

/**
 * Created by Buzz on 2020/4/23.
 * Email :lmx2060918@126.com
 */
sealed class MovieDetailViewState {
    data class NetworkError(val message: String?) : MovieDetailViewState()
    object Loading: MovieDetailViewState()
    data class Success(val movieDetail: MovieDetail?) : MovieDetailViewState()
}