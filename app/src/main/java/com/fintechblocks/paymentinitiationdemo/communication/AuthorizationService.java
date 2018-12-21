package com.fintechblocks.paymentinitiationdemo.communication;

import com.fintechblocks.paymentinitiationdemo.model.AuthCallbackPayload;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSubmissionData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthorizationService {
    @GET("/payment-init-1.0/authorization-url")
    Call<String> getAuthorizationUrl(@Query("PaymentId") String paymentId);

    @POST("/payment-init-1.0/authorization-callback")
    Call<PaymentSubmissionData> postAuthorazationCallback(@Body AuthCallbackPayload authCallbackPayload);
}
