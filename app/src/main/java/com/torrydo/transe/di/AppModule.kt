package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.data.SearchRepository
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.data.SearchRepositoryImpl
import com.torrydo.transe.dataSource.data.eng.EngSearchImpl
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Named("appModule")
object AppModule {

    @Singleton
    @Provides
    @Named("appSearchRepo")
    fun provideSearchRepository(): SearchRepository = SearchRepositoryImpl(EngSearchImpl())

    @Singleton
    @Provides
    @Named("appDbRepo")
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) : LocalDatabaseRepository = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )

}