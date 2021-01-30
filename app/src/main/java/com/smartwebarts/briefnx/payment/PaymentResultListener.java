package com.smartwebarts.briefnx.payment;

public interface PaymentResultListener {
    void onPaymentSuccess(String var1);

    void onPaymentError(int var1, String var2);
}
