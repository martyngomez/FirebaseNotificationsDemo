package com.martyngomez.firebasedemo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

// Servicio en background

public class PlatziFirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    private static final String TAG = "PlatziInstanceIdService";

    @Override
    public void onTokenRefresh() { // Se ejecuta cuando ocurra algo con el Token
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken(); //Registra y Recibe Token
        Log.w(TAG, "TokenRefresh: " + token);
    }

}
