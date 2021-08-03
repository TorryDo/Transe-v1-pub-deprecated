package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.RemoteDatabaseRepository
import com.torrydo.transe.dataSource.database.RemoteDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.database.remote.FirebaseDaoImpl
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

    @ViewModelScoped
    @Provides
    @Named("viewModelRemoteDatabase")
    fun provideRemoteDatabase(): RemoteDatabaseRepository =
        RemoteDatabaseRepositoryImpl(FirebaseDaoImpl())

}