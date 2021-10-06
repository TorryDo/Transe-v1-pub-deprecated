package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.auth.AuthenticationMethod
import com.torrydo.transe.dataSource.auth.FirebaseAuthenticationMethod
import com.torrydo.transe.dataSource.database.LocalDatabaseRepositoryImpl
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyPopupMenuHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Named

@Module
@InstallIn(FragmentComponent::class)
@Named(CONSTANT.fragmentModule)
object FragmentModule {

    @FragmentScoped
    @Provides
    @Named(CONSTANT.fragmentAuth)
    fun provideSignIn(
        @ApplicationContext context: Context
    ): AuthenticationMethod = FirebaseAuthenticationMethod(context)

    @FragmentScoped
    @Provides
    @Named(CONSTANT.fragmentPopupMenuHelper)
    fun providePopupMenuHelper(
        @ApplicationContext context: Context
    ) = MyPopupMenuHelper(context)

    @FragmentScoped
    @Provides
    @Named(CONSTANT.fragmentLocalDB)
    fun provideDatabaseRepository(
        @ApplicationContext context: Context
    ) = LocalDatabaseRepositoryImpl(
        vocabDao = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()
    )
//    @FragmentScoped
//    @Provides
//    @Named(CONSTANT.fragmentPronunciation)
//    fun providePronunciationHelper(
//        @ApplicationContext context: Context
//    ) =  PronunciationHelper(context)


}