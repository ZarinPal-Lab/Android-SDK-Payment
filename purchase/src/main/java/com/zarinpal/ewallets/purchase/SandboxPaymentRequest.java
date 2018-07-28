package com.zarinpal.ewallets.purchase;

/**
 * Created by ImanX.
 * PurchaseSDK | Copyrights 2018 ZarinPal Crop.
 */

public class SandboxPaymentRequest extends PaymentRequest {

    private static final String SANDBOX        = "sandbox.";
    private static final String WORLD_WIDE_WEB = "www.";

    @Override
    public String getPaymentRequestURL() {
        return String.format(PAYMENT_REQUEST_URL, SANDBOX)
                .replace(WORLD_WIDE_WEB, "");
    }

    @Override
    public String getStartPaymentGatewayURL(String authority) {
        return String.format(PAYMENT_GATEWAY_URL, SANDBOX, authority)
                .replace(WORLD_WIDE_WEB, "");
    }

    @Override
    public String getVerificationPaymentURL() {
        return String.format(VERIFICATION_PAYMENT_URL, SANDBOX)
                .replace(WORLD_WIDE_WEB, "");
    }
}
