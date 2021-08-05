package com.torrydo.transe.dataSource.auth

import com.torrydo.transe.dataSource.auth.models.UserAccountInfo

interface AuthenticationMethod {

    fun getUserAccountInfo(): UserAccountInfo?

    fun isSignedIn(): Boolean

    fun signOut()

}