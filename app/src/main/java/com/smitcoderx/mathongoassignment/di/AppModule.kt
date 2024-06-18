package com.smitcoderx.mathongoassignment.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.smitcoderx.mathongoassignment.api.ReciipiieApi
import com.smitcoderx.mathongoassignment.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideReciipiieApi(retrofit: Retrofit): ReciipiieApi =
        retrofit.create(ReciipiieApi::class.java)
}