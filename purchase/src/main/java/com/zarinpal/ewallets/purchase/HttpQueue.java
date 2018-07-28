package com.zarinpal.ewallets.purchase;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
class HttpQueue {
    private static HttpQueue    instance;
    private static RequestQueue queue;

    public static HttpQueue getInstance(Context context) {
        if (instance == null) {
            instance = new HttpQueue();
            queue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    public void addToRequest(Request request) {
        queue.add(request);
    }
}
