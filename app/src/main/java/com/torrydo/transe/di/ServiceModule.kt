package com.torrydo.transe.di

import android.app.Service
import android.content.Context
import android.view.WindowManager
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.image.ImageApiImpl
import com.torrydo.transe.dataSource.translation.SearchRepositoryImpl
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.vocabsource.VocabSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
@Named(CONSTANT.serviceModule)
object ServiceModule {

    @ServiceScoped
    @Provides
    @Named(CONSTANT.serviceWindowManager)
    fun provideWindowService(
        @ApplicationContext context: Context
    ) = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager

    @ServiceScoped
    @Provides
    @Named(CONSTANT.serviceSearchRepo)
    fun provideSearchRepository() = SearchRepositoryImpl(
        vocabSource = VocabSource(),
        imageSearch = ImageApiImpl()
    )

    @ServiceScoped
    @Provides
    @Named(CONSTANT.serviceLocalDB)
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )

}