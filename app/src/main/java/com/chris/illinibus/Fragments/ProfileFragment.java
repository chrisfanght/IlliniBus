package com.chris.illinibus.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chris.illinibus.IlliniBusApplication;
import com.chris.illinibus.R;
import com.chris.illinibus.StartActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Profile fragment for basic information of the current user
 * Created by Chris on 11/5/16.
 */

public class ProfileFragment extends Fragment {

    private IlliniBusApplication mApplication;
    private ImageView mProfileImage;
    private TextView mUsername;
    private TextView mEmail;
    private Button mAccountButton;
    private View mProfileInfo;

    /**
     * Initialize the layout view for Profile Fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mApplication = (IlliniBusApplication) getActivity().getApplication();
        mUsername = (TextView) view.findViewById(R.id.username);
        mEmail = (TextView) view.findViewById(R.id.email);
        mProfileImage = (ImageView) view.findViewById(R.id.profile_image);
        mAccountButton = (Button) view.findViewById(R.id.account_button);
        mProfileInfo = view.findViewById(R.id.profile_layout);
        setupAccountInfo();
        setupButtonAction();
        return view;
    }

    /**
     * Set up account detail information if user successfully logged in
     */
    public void setupAccountInfo() {
        if (mApplication.isSignedIn()) {
            mProfileInfo.setVisibility(View.VISIBLE);
            GoogleSignInAccount account = mApplication.getGoogleAccount();
            Glide.with(getContext())
                    .load(account.getPhotoUrl())
                    .into(mProfileImage);
            mUsername.setText(account.getDisplayName());
            mEmail.setText(account.getEmail());
            mAccountButton.setText("Log out");
        } else {
            mProfileInfo.setVisibility(View.GONE);
            mAccountButton.setText("Log in");
        }
    }

    /**
     * Set up login and logout button
     */
    public void setupButtonAction() {
        if (mApplication.isSignedIn()) {
            mAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApplication.signOut();
                    setupAccountInfo();
                    setupButtonAction();
                }
            });
        } else {
            mAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
