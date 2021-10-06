package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyPopupMenuHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
@Named(CONSTANT.activityModule)
object ActivityModule {

    @ActivityScoped
    @Provides
    @Named(CONSTANT.activityPopupMenuHelper)
    fun providePopupMenuHelper(
        @ApplicationContext context: Context
    ) = MyPopupMenuHelper(context)

    @ActivityScoped
    @Provides
    @Named(CONSTANT.activityLocalDB)
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )
//    @ActivityScoped
//    @Provides
//    @Named(CONSTANT.activityPronunciation)
//    fun providePronunciationHelper(
//        @ApplicationContext context: Context
//    ) =  PronunciationHelper(context)

}