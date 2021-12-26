package dev.cynomys.movieapp.businesslogic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.cynomys.movieapp.BuildConfig
import dev.cynomys.movieapp.businesslogic.api.MovieApiService
import dev.cynomys.movieapp.businesslogic.repositories.MovieApiRepo
import dev.cynomys.movieapp.businesslogic.repositories.MovieApiRepoImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun providesMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    fun providesMovieApiRepo(remoteService: MovieApiService): MovieApiRepo =
        MovieApiRepoImpl(remoteService)
}