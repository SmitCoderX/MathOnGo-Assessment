package com.smitcoderx.mathongoassignment.di

import android.app.Application
import androidx.room.Room
import com.smitcoderx.mathongoassignment.db.ReciipiieDatabase
import com.smitcoderx.mathongoassignment.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, ReciipiieDatabase::class.java, Constants.EVENT_DB)
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideReciipiieDao(db: ReciipiieDatabase) = db.recipeDao()

}