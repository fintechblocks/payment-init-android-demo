package com.fintechblocks.paymentinitiationdemo.communication;

import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupGETResponse;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupPOSTRequest;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupPOSTResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PaymentsService {

    @POST("/payment-init-1.0/open-banking/v1.1/payments")
    Call<PaymentSetupPOSTResponse> postPayments(
            @Body PaymentSetupPOSTRequest paymentSetupPOSTRequest,
            @Header("x-idempotency-key") String idempotencyKey,
            @Header("Authorization") String authorization);
}
