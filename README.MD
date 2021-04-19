# ZarinPal In App Purchase Android SDK

ZarinPal Payment Requeset SDK on Android Platforms
Simply Request to ZarinPal IPG and Callback Handling


### ZarinPal Flow:
In first step You must initilize of `ZarinPal` by `getPurchase` after that, ZarinPal Ready to Payment. for begining you must create a `PaymentRequest` by `getPaymentRequest`, Now you must set parameters `MerchantID`, `Amount (In Rials)`, `Description` and `CallbackUrl`

- What is `Payment Request`?
Payment Request is requirment paramaters payment for Indentity in ZarinPal

**Require Parameters:**

*   Merchant id: An unique ID of your business payment gateway.
*   Amount: Amount of Purchase.
*   Callback URL: A valid `URI` or `URL` Address for sending result purchase.
*   Description: A Content for showing to payer.

**Optional Parameters:**

*   Mobile: Valid Mobile number of payer.
*   Email: Valid Email Address of payer.

You send a PaymentRequest for ZarinPal with parameters MerchantID, Amount (In Rials), Description and CallbackUrl . Then ZarinPal will reply to you with a unique purchase ID (Authority).
Then with the Authority in Hand, You call `startPayment` method and ZarinPal in response will direct you to a payment gateway if the purchase ID is valid and after completing the purchase , Zarinpal send Purchase Result to CallbackUrl.
After completing the purchase, you can send a `verificationPayment` request if it is successful, and ZarinPal will send you a RefID, which represents the transaction ID, in case of success.

for verifty purchase you must set `getIntent().getData()` and call `verificationPayment` for verify inner app sure ZarinPal don't recommend to use this method becuase this is not safe. That's better you do verifing flow on your server and notify your app.

NOTE> ZarinPal is not IAB or IAP, ZarinPal JUST provides payment solution.


How to use
==========


- Compile ZarinPal In App Purchase SDK:
```Gradle
   implementation 'com.zarinpal:purchase:0.0.10'
 ```
- Internet Access Permission on `AndroidManifest.xml`:
 
```XML
    <uses-permission android:name="android.permission.INTERNET"/>
```
- Set Your Application Scheme on `AndroidManifest.xml` for Callback for inner handling in App:
```XML
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>

         <category android:name="android.intent.category.DEFAULT"/>
         <category android:name="android.intent.category.BROWSABLE"/>

         <data android:scheme="<YOUR-APP-SCHEME>"/>
     </intent-filter>
```
Note> If You want handle and checking of verification your Payment on Your sever you must set your server address (aka `URL`) by `setCallbackURL` and no need to do the above step.

### Example For Payment Request:
 ```Java
        ZarinPal       purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment  = ZarinPal.getPaymentRequest();
        //If you will test on our sandbox, you can use it:
        PaymentRequest payment  = ZarinPal.getSandboxPaymentRequest();



        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(100);
        payment.isZarinGateEnable(true);  // If you actived `ZarinGate`, can handle payment by `ZarinGate`
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


### Example For Callback Handler:
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
 
### New Options:

* Support ZarinGate in 0.0.4 Version
* Implemented SandBox Payment in 0.0.8 Version
* `ZarinGate` is Optional attribute in 0.0.10 Version



Developed By
============

The Product developed by ZarinPal Team also You can Communicate and open issue
>Note: This version is open source then you can open pull request and we check it, If project
need to your change, We will certainly merge it.

