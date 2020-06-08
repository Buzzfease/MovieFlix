package top.hanyue.movieflex.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import top.hanyue.movieflex.content.detail.MovieDetailViewModel
import top.hanyue.movieflex.content.main.CategoryViewModel
import top.hanyue.movieflex.content.main.MainViewModel
import top.hanyue.movieflex.content.play.MoviePlayViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MovieFlexViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviePlayViewModel::class)
    abstract fun bindMoviePlayViewModel(moviePlayViewModel: MoviePlayViewModel): ViewModel

}