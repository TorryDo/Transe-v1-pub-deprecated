package com.torrydo.transeng.di

import android.content.Context
import com.torrydo.transeng.dataSource.database.LocalDatabaseRepository
import com.torrydo.transeng.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transeng.dataSource.database.local.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
@Named("viewModelModule")
object ViewModelModule {


    @ViewModelScoped
    @Provides
    @Named("viewModelDbRepo")
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ): LocalDatabaseRepository = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )

    @ViewModelScoped
    @Provides
    @Named("viewModelString")
    fun provideString() = "I am providing a string"

}