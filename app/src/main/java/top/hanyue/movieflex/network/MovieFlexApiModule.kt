package top.hanyue.movieflex.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object MovieFlexApiModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideTvMazeApi(
        okHttpClient: OkHttpClient,
        @Named(NetworkModule.MOVIE_FLEX_BASE_URL) baseUrl: String
    ): MovieFlexApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create(MovieFlexApi::class.java)
    }
}