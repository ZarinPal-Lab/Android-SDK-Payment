package com.zarinpal.ewallets.purchase;

/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public interface OnCallbackVerificationPaymentListener {
    void onCallbackResultVerificationPayment(boolean isPaymentSuccess,
                                             String refID,
                                             PaymentRequest paymentRequest);
}
