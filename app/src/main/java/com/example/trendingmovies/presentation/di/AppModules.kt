package com.example.trendingmovies.presentation.di

import com.example.trendingmovies.data.api.MovieApiInterface
import com.example.trendingmovies.repository.MovieRepository
import com.example.trendingmovies.util.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModules {
    @Singleton
    @Provides
    fun provideApiInterface(): MovieApiInterface {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(MovieApiInterface::class.java)
    }

    @Provides
    fun provideMovieRepository(movieApiInterface: MovieApiInterface):MovieRepository{
        return MovieRepository(movieApiInterface)
    }
}