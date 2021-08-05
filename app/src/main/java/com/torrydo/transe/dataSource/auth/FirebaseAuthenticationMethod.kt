package com.torrydo.transe.dataSource.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.torrydo.transe.R
import com.torrydo.transe.dataSource.auth.models.UserAccountInfo

class FirebaseAuthenticationMethod(
    val context: Context
) : AuthenticationMethod {

    private val TAG = "_TAG_FirebaseSignInMethod"

    private var auth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null


    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)

        auth = FirebaseAuth.getInstance()

    }

    override fun getUserAccountInfo(): UserAccountInfo? {

        if (isSignedIn()) {
            val uid = auth?.uid ?: "null"
            val name = auth?.currentUser?.displayName ?: "null"
            val email = auth?.currentUser?.email ?: "null"

            return UserAccountInfo(
                uid,
                name,
                email
            )
        }

        return null
    }

    override fun isSignedIn() = auth?.currentUser != null

    override fun signOut() {
        auth?.signOut()
        googleSignInClient?.signOut()
    }


}