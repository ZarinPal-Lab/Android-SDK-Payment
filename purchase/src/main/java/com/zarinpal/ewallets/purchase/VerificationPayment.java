package com.zarinpal.ewallets.purchase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
class VerificationPayment {

    private long   amount;
    private String authority;
    private String merchantID;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }


    public JSONObject getVerificationPaymentAsJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(Payment.AUTHORITY_PARAMS, getAuthority());
        object.put(Payment.AMOUNT_PARAMS, getAmount());
        object.put(Payment.MERCHANT_ID_PARAMS, getMerchantID());
        return object;
    }
}
