package com.torrydo.transeng.di

import android.content.Context
import com.torrydo.transeng.dataSource.data.SearchRepository
import com.torrydo.transeng.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transeng.dataSource.database.local.MyRoomDatabase
import com.torrydo.transeng.dataSource.data.SearchRepositoryImpl
import com.torrydo.transeng.dataSource.data.eng.EngSearchImpl
import com.torrydo.transeng.dataSource.database.LocalDatabaseRepository
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