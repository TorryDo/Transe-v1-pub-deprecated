package com.torrydo.transe.di

import android.content.Context
import com.torrydo.transe.dataSource.signin.FirebaseAuthenticationMethod
import com.torrydo.transe.dataSource.signin.AuthenticationMethod
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
@Named("fragmentModule")
object FragmentModule {

    @ViewModelScoped
    @Provides
    @Named("fragmentSignIn")
    fun provideSignIn(
        @ApplicationContext context: Context
    ): AuthenticationMethod = FirebaseAuthenticationMethod(context)


}