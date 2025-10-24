package com.example.jokefinder.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class NetworkLiveData extends LiveData<Boolean> {
    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback networkCallback;

    public NetworkLiveData(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                postValue(true);
            }

            @Override
            public void onLost(@NonNull Network network) {
                postValue(false);
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        boolean connected = false;
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities cap = connectivityManager.getNetworkCapabilities(network);
            connected = cap != null &&
                    (cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        postValue(connected);

        NetworkRequest request = new NetworkRequest.Builder().build();
        connectivityManager.registerNetworkCallback(request, networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
