package com.zarinpal.ewallets.purchasesdk;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getData() != null) {

            ZarinPal.getPurchase(this).verificationPayment(getIntent().getData(), new OnCallbackVerificationPaymentListener() {
                @Override
                public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
                    Log.i("TAG", "onCallbackResultVerificationPayment: " + refID);
                }
            });
        }

        Button btnPay = (Button) findViewById(R.id.btnPayment);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PaymentRequest payment = ZarinPal.getPaymentRequest();


                payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
                payment.setAmount(120);
                payment.setDescription("In App Purchase Test SDK");
                payment.setCallbackURL("app://app");
                payment.setMobile("09355106005");
                payment.setEmail("imannamix@gmail.com");
                payment.isZarinGateEnable(false);


                ZarinPal.getPurchase(getApplicationContext()).startPayment(payment, new OnCallbackRequestPaymentListener() {
                    @Override
                    public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                        startActivity(intent);
                    }
                });

            }
        });


    }

}
