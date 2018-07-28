# ZarinPal In App Purchase Android SDK

ZarinPal Payment Requeset SDK on Android Platforms
Simply Request to ZarinPal IPG and Callback Handling

### New Options:

* Support ZarinGate in 0.0.4 Version
* Implemented SandBox Payment in 0.0.4 Version

>Note: This version is open source then you can open pull request and we check it, If project
need to your change, We will certainly merge it.





- Compile ZarinPal In App Purchase SDK:
```Gradle
   compile 'com.zarinpal:purchase:0.0.8-beta'
 ```
- Internet Access Permission:
 
```XML
    <uses-permission android:name="android.permission.INTERNET"/>
```
- Set Your Application Scheme in Actvity for Handling Callback: 
```XML
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>

         <category android:name="android.intent.category.DEFAULT"/>
         <category android:name="android.intent.category.BROWSABLE"/>

         <data android:scheme="<YOUR-APP-SCHEME>"/>
     </intent-filter>
```
###Example For Payment Request:
 ```Java
        ZarinPal       purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment  = ZarinPal.getPaymentRequest();
        //If you will test on our sandbox, you can use it:
        PaymentRequest payment  = ZarinPal.getSandboxPaymentRequest();



        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(100);
        payment.setDescription("In App Purchase Test SDK");
        payment.setCallbackURL("yourapp://app");     /* Your App Scheme */
        payment.setMobile("09355106005");            /* Optional Parameters */
        payment.setEmail("imannamix@gmail.com");     /* Optional Parameters */


        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {


                if (status == 100) {
                    /*
                    When Status is 100 Open Zarinpal PG on Browser
                    */
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
                }

            }
        });
 ```


###Example For Callback Handler:
 ```Java
         /**
         * When User Return to Application From IPG on Browser
         */
         Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "Your Payment is Success :) " + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    /* When Payment Request is Failure :) */
                    String message = "Your Payment is Failure :(";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }


            }
        });

 ```
