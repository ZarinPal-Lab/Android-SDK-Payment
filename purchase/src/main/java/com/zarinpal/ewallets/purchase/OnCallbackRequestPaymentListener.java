package com.zarinpal.ewallets.purchase;


import android.content.Intent;
import android.net.Uri;

/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public interface OnCallbackRequestPaymentListener {
    void onCallbackResultPaymentRequest(int status,
                                        String authority,
                                        Uri paymentGatewayUri,
                                        Intent intent);
}
