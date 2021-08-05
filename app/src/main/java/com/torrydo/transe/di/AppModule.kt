package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.translation.SearchRepository
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.translation.SearchRepositoryImpl
import com.torrydo.transe.dataSource.translation.eng.EngSearchImpl
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.utils.CONSTANT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Named(CONSTANT.appModule)
object AppModule {

    @Singleton
    @Provides
    @Named(CONSTANT.appSearchRepo)
    fun provideSearchRepository(): SearchRepository = SearchRepositoryImpl(EngSearchImpl())

    @Singleton
    @Provides
    @Named(CONSTANT.appLocalDB)
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) : LocalDatabaseRepository = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )

}