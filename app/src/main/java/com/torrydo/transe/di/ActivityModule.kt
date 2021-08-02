package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.data.eng.pronunciation.PronunciationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
@Named("activityModule")
object ActivityModule {

    @ActivityScoped
    @Provides
    @Named("activityDbRepo")
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )
    @ActivityScoped
    @Provides
    @Named("activityPronunciationHelper")
    fun providePronunciationHelper(
        @ApplicationContext context: Context
    ) =  PronunciationHelper(context)

}