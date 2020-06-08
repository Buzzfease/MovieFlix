package top.hanyue.movieflex.network

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import top.hanyue.movieflex.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [MovieFlexApiModule::class])
object NetworkModule {
    const val MOVIE_FLEX_BASE_URL = "movie_flex_base_url"
    private const val BASE_URL = "http://fanq.wy2sf.com/"

    @JvmStatic
    @Provides
    @Named(MOVIE_FLEX_BASE_URL)
    fun provideBaseUrlString(): String {
        return BASE_URL
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheSize = 5 * 1024 * 1024 // 5 MB
        val cacheDir = context.cacheDir
        return Cache(cacheDir, cacheSize.toLong())
    }
}