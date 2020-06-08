package top.hanyue.movieflex.network

import retrofit2.http.GET
import retrofit2.http.Query
import top.hanyue.movieflex.network.main.MovieDetail
import top.hanyue.movieflex.network.main.Navigation
import top.hanyue.movieflex.network.main.Recommend

interface MovieFlexApi {

    //首页导航
    @GET("vod/column")
    suspend fun getNavigationColumn(): Navigation


    //分类推荐
    @GET("vod/conf")
    suspend fun getRecommendList(@Query("module") module:String,
                                 @Query("page") page:String,
                                 @Query("limit") limit:String) : Recommend
    
    
    //根据id获取电影详情 http://fanq.wy2sf.com/vod/vodDetail?uid=6954&vod_id=34102
    @GET("vod/vodDetail")
    suspend fun getMovieDetail(@Query("uid") uid:String,
                               @Query("vod_id") vodId:String): MovieDetail

}