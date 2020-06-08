package top.hanyue.movieflex.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import top.hanyue.movieflex.content.detail.MovieDetailActivity
import top.hanyue.movieflex.content.main.CategoryFragment
import top.hanyue.movieflex.content.main.MainActivity
import top.hanyue.movieflex.content.play.MoviePlayActivity

/**
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun bindCategoryFragment() : CategoryFragment


    @ContributesAndroidInjector
    abstract fun bindMovieDetailActivity() : MovieDetailActivity

    @ContributesAndroidInjector
    abstract fun bindMoviePlayActivity() : MoviePlayActivity
}
