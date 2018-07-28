package com.zarinpal.ewallets.purchase;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public class HttpRequest implements Response.Listener, Response.ErrorListener {

    private Request             request;
    private Context             context;
    private String              url;
    private int                 timeOut;
    private int                 requestMethod;
    private byte                requestType;
    private JSONObject          jsonObject;
    private HttpRequestListener listener;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params  = new HashMap<>();


    public static final  int    GET                       = Request.Method.GET;    /* Request Method Fields */
    public static final  int    POST                      = Request.Method.POST;     /* Request Method Fields */
    public static final  int    PUT                       = Request.Method.PUT;     /* Request Method Fields */
    public static final  int    DELETE                    = Request.Method.DELETE;     /* Request Method Fields */
    public static final  byte   RAW                       = 0;    /* Request EasyPayType Fields */
    public static final  byte   FROM_DATA                 = 1;    /* Request EasyPayType Fields */
    private static final int    TIMEOUT_DEFAULT_VALUE     = 10 * 1000;
    public static final  int    INTERNET_CONNECTION_ERROR = -100;
    public static final  int    TIMEOUT_ERROR             = -101;
    public static final  int    UNKNOWN_ERROR             = -102;
    private static final String DEFAULT_ERROR             = "Http error incorrect.";

    public HttpRequest(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public HttpRequest setParams(Map<String, String> params) { /*this method set Parameters for Request Method POST */
        this.params = params;
        return this;

    }

    public HttpRequest setJson(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public HttpRequest setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest setRequestType(byte RequestType) {  /*this method set Request EasyPayType : JSON || STRING */
        this.requestType = RequestType;
        return this;
    }

    public HttpRequest setRequestMethod(int method) { /*this method set Request Method : POST || GET*/
        requestMethod = method;
        return this;
    }

    public HttpRequest setTimeOut(int timeOut) {
        this.timeOut = (timeOut * 1000);
        return this;
    }

    @Override
    public void onResponse(Object response) {

        if (!isJsonValid(response)) {
            listener.onSuccessResponse(null, response.toString());
            return;
        }

        try {
            listener.onSuccessResponse(new JSONObject(response.toString()), response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (error instanceof NoConnectionError) {
            listener.onFailureResponse(INTERNET_CONNECTION_ERROR, DEFAULT_ERROR);
            return;
        }

        if (error instanceof TimeoutError) {
            listener.onFailureResponse(TIMEOUT_ERROR, DEFAULT_ERROR);
            return;
        }

        if (error.networkResponse == null) {
            listener.onFailureResponse(UNKNOWN_ERROR, DEFAULT_ERROR);
            return;
        }

        listener.onFailureResponse(error.networkResponse.statusCode, new String(error.networkResponse.data));

        Log.i("TAG Error HttpRequest", new String(error.networkResponse.data));
    }


    public void get(HttpRequestListener listener) {
        this.listener = listener;
        if (requestType == FROM_DATA) {

            this.request = new StringRequest(requestMethod, url, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }
            };

        } else {

            this.request = new JsonObjectRequest(requestMethod, url, (jsonObject == null ? new JSONObject(params) : jsonObject), this, this) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }
            };
        }

        this.request.setRetryPolicy(new DefaultRetryPolicy(
                (this.timeOut == 0) ? TIMEOUT_DEFAULT_VALUE : timeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        HttpQueue.getInstance(context).addToRequest(request);

    }

    private boolean isJsonValid(Object object) {
        try {
            new JSONObject(object.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
