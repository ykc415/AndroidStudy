package com.androidhuman.example.simplecontacts.model;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Person extends RealmObject {

    public static final String PRIMARY_KEY = "id";

    @PrimaryKey
    private long id;

    @Required
    private String name;

    @Nullable
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}
