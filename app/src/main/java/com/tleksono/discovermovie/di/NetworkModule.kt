package com.tleksono.discovermovie.di

import android.content.Context
import com.tleksono.discovermovie.BuildConfig
import com.tleksono.discovermovie.common.NetworkHelper
import com.tleksono.discovermovie.data.source.remote.Apis
import com.tleksono.discovermovie.data.source.remote.ApisService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDefaultOkHttp(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            addInterceptor(interceptor)
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiKey() = Interceptor { chain: Interceptor.Chain ->
        val url = chain
            .request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        return@Interceptor chain.proceed(
            chain.request().newBuilder().url(url).build()
        )
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Apis = ApisService(retrofit)

}
