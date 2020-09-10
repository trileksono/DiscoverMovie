package com.tleksono.discovermovie.di

import com.tleksono.discovermovie.ui.mainactivity.MainActivityRouter
import com.tleksono.discovermovie.ui.movies.MovieRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouterModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun provideMainRouter(router: Router): MainActivityRouter = MainActivityRouter(router)

    @Provides
    @Singleton
    fun provideMovieRouter(router: Router): MovieRouter = MovieRouter(router)
}