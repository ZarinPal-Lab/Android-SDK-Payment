package com.zarinpal.ewallets.purchase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Android ZarinPal In App Purchase SDK Library v0.0.2 Beta Project.
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
@SuppressLint("StaticFieldLeak")
public class ZarinPal {


    private static ZarinPal       instance;
    private        Context        context;
    private        PaymentRequest paymentRequest;


    public static ZarinPal getPurchase(Context context) {
        if (instance == null) {
            instance = new ZarinPal(context);
        }
        return instance;
    }


    public static PaymentRequest getPaymentRequest() {
        return new PaymentRequest();
    }

    public static SandboxPaymentRequest getSandboxPaymentRequest() {
        return new SandboxPaymentRequest();
    }

    private ZarinPal(Context context) {
     /* ZarinPal Class blocked create a instance  */
        this.context = context;
    }

    public void verificationPayment(Uri uri, final OnCallbackVerificationPaymentListener listener) {

        if (uri == null || paymentRequest == null || !uri.isHierarchical()) {
            return;
        }


        boolean isSuccess = uri.getQueryParameter("Status").equals("OK");
        String  authority = uri.getQueryParameter("Authority");

        if (!authority.equals(paymentRequest.getAuthority())) {
            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
            return;
        }

        if (!isSuccess) {
            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
            return;
        }


        VerificationPayment verificationPayment = new VerificationPayment();
        verificationPayment.setAmount(this.paymentRequest.getAmount());
        verificationPayment.setMerchantID(this.paymentRequest.getMerchantID());
        verificationPayment.setAuthority(authority);

        try {
            new HttpRequest(context, paymentRequest.getVerificationPaymentURL())
                    .setJson(verificationPayment.getVerificationPaymentAsJson())
                    .setRequestMethod(HttpRequest.POST)
                    .setRequestType(HttpRequest.RAW)
                    .get(new HttpRequestListener() {
                        @Override
                        public void onSuccessResponse(JSONObject jsonObject, String contentResponse) {
                            try {
                                String refID = jsonObject.getString("RefID");
                                listener.onCallbackResultVerificationPayment(true, refID, paymentRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailureResponse(int httpStatusCode, String dataError) {
                            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
                        }
                    });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startPayment(final PaymentRequest paymentRequest, final OnCallbackRequestPaymentListener listener) {

        this.paymentRequest = paymentRequest;

        try {
            new HttpRequest(context, paymentRequest.getPaymentRequestURL())
                    .setRequestType(HttpRequest.RAW)
                    .setRequestMethod(HttpRequest.POST)
                    .setJson(paymentRequest.getPaymentRequestAsJson())
                    .get(new HttpRequestListener() {
                        @Override
                        public void onSuccessResponse(JSONObject jsonObject, String contentResponse) {

                            try {
                                int    status    = jsonObject.getInt("Status");
                                String authority = jsonObject.getString("Authority");
                                paymentRequest.setAuthority(authority);

                                // ChromeTab.launch(mContext, URLPaymentBuilder.getAuthorityPaymentURL(authority));

                                Uri    uri    = Uri.parse(paymentRequest.getStartPaymentGatewayURL(authority));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                listener.onCallbackResultPaymentRequest(status, authority, uri, intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailureResponse(int httpStatusCode, String dataError) {

                            try {
                                JSONObject object = new JSONObject(dataError);
                                int        status = object.getInt("Status");
                                listener.onCallbackResultPaymentRequest(status, null, null, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
