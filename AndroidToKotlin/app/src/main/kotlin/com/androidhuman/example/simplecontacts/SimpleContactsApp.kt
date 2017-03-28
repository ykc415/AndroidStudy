package com.androidhuman.example.simplecontacts

import android.app.Application

import io.realm.Realm

class SimpleContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
