package com.fintechblocks.paymentinitiationdemo;

import android.content.Intent;
import android.net.Uri;

import net.openid.appauth.RedirectUriReceiverActivity;

public class HybridFlowRedirectUriReceiverActivity extends RedirectUriReceiverActivity {

    @Override
    public Intent getIntent() {
        Intent intent = super.getIntent();
        Uri uri = Uri.parse(super.getIntent().getDataString());
        return intent.setData(uri);
    }
}
