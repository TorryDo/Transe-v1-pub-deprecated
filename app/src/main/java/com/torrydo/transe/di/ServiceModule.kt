package com.torrydo.transe.di

import android.app.Service
import android.content.Context
import android.view.WindowManager
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.data.SearchRepositoryImpl
import com.torrydo.transe.dataSource.data.eng.EngSearchImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
@Named("launcherService")
object ServiceModule {

    @ServiceScoped
    @Provides
    @Named("wm1")
    fun provideWindowService(
        @ApplicationContext context: Context
    ) = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager

    @ServiceScoped
    @Provides
    @Named("searchRepo")
    fun provideSearchRepository() = SearchRepositoryImpl(EngSearchImpl())

    @ServiceScoped
    @Provides
    @Named("dbRepo")
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )

}