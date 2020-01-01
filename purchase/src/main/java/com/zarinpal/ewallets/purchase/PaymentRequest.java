package com.zarinpal.ewallets.purchase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public class PaymentRequest {


    protected static final String PAYMENT_GATEWAY_URL      = "https://www.%szarinpal.com/pg/StartPay/%s/%s";
    protected static final String PAYMENT_REQUEST_URL      = "https://www.%szarinpal.com/pg/rest/WebGate/PaymentRequest.json";
    protected static final String VERIFICATION_PAYMENT_URL = "https://www.%szarinpal.com/pg/rest/WebGate/PaymentVerification.json";


    private String  merchantID;
    private long    amount;
    private String  mobile;
    private String  email;
    private String  description;
    private String  callBackURL;
    private String  authority;
    private boolean isZarinGateEnable;


    public boolean isZarinGateEnable() {
        return isZarinGateEnable;
    }

    public void isZarinGateEnable(boolean zarinGateEnable) {
        isZarinGateEnable = zarinGateEnable;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public void setCallbackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getAmount() {
        return amount;
    }


    public String getDescription() {
        return description;
    }


    public String getMobile() {
        return mobile;
    }


    public String getMerchantID() {
        return merchantID;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthority() {
        return authority;
    }

    public JSONObject getPaymentRequestAsJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Payment.MERCHANT_ID_PARAMS, getMerchantID());
        jsonObject.put(Payment.AMOUNT_PARAMS, getAmount());
        jsonObject.put(Payment.DESCRIPTION_PARAMS, getDescription());
        jsonObject.put(Payment.CALLBACK_URL_PARAMS, getCallBackURL());
        jsonObject.put(Payment.MOBILE_PARAMS, getMobile()); //Optional Value
        jsonObject.put(Payment.EMAIL_PARAMS, getEmail());//Optional Value
        return jsonObject;
    }

    public String getStartPaymentGatewayURL(String authority) {
        return String.format(PAYMENT_GATEWAY_URL, "", authority, isZarinGateEnable ? "ZarinGate" : "");
    }

    public String getPaymentRequestURL() {
        return String.format(PAYMENT_REQUEST_URL, "");
    }

    public String getVerificationPaymentURL() {
        return String.format(VERIFICATION_PAYMENT_URL, "");
    }


}
