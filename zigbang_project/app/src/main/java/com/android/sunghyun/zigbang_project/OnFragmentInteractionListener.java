package com.android.sunghyun.zigbang_project;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by YKC on 2016. 11. 8..
 */
public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
    DatabaseReference getRoomReference();
}