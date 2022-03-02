package com.example.azureobserver.myapplication.di

import com.example.azureobserver.myapplication.data.EndPoints
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }


    @Provides
    fun provideRetrofitClient(okHttpClient : OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl("https://bitwoo.servicebus.windows.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    @Provides
    fun provideApiDefinitionService(retrofit: Retrofit) =
        retrofit.create(EndPoints::class.java)


}