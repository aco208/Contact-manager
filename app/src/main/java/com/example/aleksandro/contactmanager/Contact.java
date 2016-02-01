package com.example.aleksandro.contactmanager;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Aleksandro on 3-1-2016.
 */
public class Contact {

    private String _naam, _tel, _email, _adres;
    private Uri _imageURI;

    public Contact (String naam, String tel, String email, String adres, Uri imageURI){
        _naam = naam;
        _tel = tel;
        _email = email;
        _adres = adres;
        _imageURI = imageURI;
    }

    public String getNaam() {
        return _naam;
    }

    public String getTel() {
        return _tel;
    }

    public String getEmail() {return _email; }

    public String getAdres() {return _adres; }

    public Uri getImageURI() {return _imageURI; }
}
