package com.chris.illinibus;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Chris on 11/9/16.
 */

public class IlliniBusApplication extends Application {

    private boolean mSignedIn = false;
    private GoogleSignInAccount mGoogleAccount;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Sign in using google account
     *
     * @param googleSignInAccount
     */
    public void signIn(GoogleSignInAccount googleSignInAccount) {
        mSignedIn = true;
        mGoogleAccount = googleSignInAccount;
    }

    /**
     * Sign out the account
     */
    public void signOut() {
        mSignedIn = false;
    }

    /**
     * Get google account information once a user signed in
     *
     * @return
     */
    public GoogleSignInAccount getGoogleAccount() {
        return mGoogleAccount;
    }

    /**
     * Check if a user has signed in or not
     *
     * @return
     */
    public boolean isSignedIn() {
        return mSignedIn;
    }
}
