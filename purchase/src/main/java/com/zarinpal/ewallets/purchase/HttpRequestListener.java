package com.zarinpal.ewallets.purchase;

import org.json.JSONObject;

/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public interface HttpRequestListener {
    void onSuccessResponse(JSONObject jsonObject, String contentResponse);

    void onFailureResponse(int httpStatusCode, String dataError);
}
