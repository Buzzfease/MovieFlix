package top.hanyue.movieflex.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import top.hanyue.movieflex.MovieFlexApplication
import top.hanyue.movieflex.network.MovieFlexApi
import top.hanyue.movieflex.network.NetworkModule
import top.hanyue.movieflex.utils.glide.GlideApi
import top.hanyue.movieflex.utils.glide.GlideModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuildersModule::class, NetworkModule::class, GlideModule::class])
interface AppComponent {
    fun inject(movieFlexApplication: MovieFlexApplication)

    fun inject(glideApi: GlideApi)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun glideModule(glideModule: GlideModule): Builder

        fun build(): AppComponent
    }
}
