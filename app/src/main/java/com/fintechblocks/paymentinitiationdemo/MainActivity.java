package com.fintechblocks.paymentinitiationdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fintechblocks.paymentinitiationdemo.communication.AuthorizationService;
import com.fintechblocks.paymentinitiationdemo.communication.PaymentsService;
import com.fintechblocks.paymentinitiationdemo.communication.RestApiClientInstance;
import com.fintechblocks.paymentinitiationdemo.model.AuthCallbackPayload;
import com.fintechblocks.paymentinitiationdemo.model.CreditorAccount;
import com.fintechblocks.paymentinitiationdemo.model.CreditorAgent;
import com.fintechblocks.paymentinitiationdemo.model.DebtorAccount;
import com.fintechblocks.paymentinitiationdemo.model.DebtorAgent;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupInitiation;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupInitiationData;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupInitiationInstructedAmount;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupPOSTRequest;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSetupPOSTResponse;
import com.fintechblocks.paymentinitiationdemo.model.PaymentSubmissionData;
import com.fintechblocks.paymentinitiationdemo.model.RemittanceInformation;
import com.fintechblocks.paymentinitiationdemo.model.Risk;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.browser.BrowserWhitelist;
import net.openid.appauth.browser.VersionedBrowserMatcher;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // Constants
    final static int AUTH_REQUEST_CODE = 1;
    private static final String SHARED_PREFERENCES_NAME = "AuthStatePreference";
    private static final String AUTH_STATE = "AUTH_STATE";
    private static final String EMPTY_STRING = "";

    // Fields

    // Fields / Views
    private TextInputLayout mBackendUrl;
    private CheckBox mWithdebtorDataCheckbox;
    private Button mCreatePaymentButton;

    // Fields / Views / Instructed amount
    private TextInputLayout mInstructedAmount;
    private TextInputLayout mInstructedAmountCurrency;

    // Fields / Views / Creditor account
    private TextInputLayout mCreditorAccountIdentification;
    private TextInputLayout mCreditorAccountName;
    private TextInputLayout mCreditorAccountSchemeName;
    private TextInputLayout mCreditorSecondaryIdentification;

    // Fields / Views / Creditor agent
    private TextInputLayout mCreditorAgentSchemeName;
    private TextInputLayout mCreditorAgentIdentification;

    // Fields / Views / Remittance information
    private TextInputLayout mRemittanceInformationReference;
    private TextInputLayout mRemittanceInformationUnstructured;
    private TextInputLayout mInstructionIdentification;
    private TextInputLayout mEndToEndIdentification;

    // Fields / Views / Debtor agent
    private TextInputLayout mDebtorAgentSchemeName;
    private TextInputLayout mDebtorAgentIdentification;

    // Fields / Views / Debtor account
    private ConstraintLayout mDebtorDataFields;
    private TextInputLayout mDebtorAccountSchemeName;
    private TextInputLayout mDebtorAccountIdentification;
    private TextInputLayout mDebtorAccountName;
    private TextInputLayout mDebtorAccountSecondaryIdentification;

    // Fields / Views / Status
    private TextView mStatusTextView;
    private TextView mErrorTextView;

    // Fields  / Auth
    private AuthState mAuthState;
    private net.openid.appauth.AuthorizationService mAuthorizationService;

    // Activity overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find UI views and fill sample data
        initializeUiFields();
        fillSampleData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTH_REQUEST_CODE) {
            AuthorizationResponse authorizationResponse = AuthorizationResponse.fromIntent(data);
            AuthorizationException authorizationException = AuthorizationException.fromIntent(data);

            if (authorizationResponse != null) {
                postAuthenticationCallback(authorizationResponse.authorizationCode, authorizationResponse.state);
            }

            if (authorizationException != null) {
                mErrorTextView.setText(authorizationException.errorDescription);
                mErrorTextView.setVisibility(View.VISIBLE);
                mCreatePaymentButton.setEnabled(true);
            }
        }
    }

    // Listeners
    /**
     * Changes the debtor input views visibility according to the checkbox state.
     */
    private CompoundButton.OnCheckedChangeListener mWithDebtorDataCheckedChanageListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mDebtorDataFields.setVisibility(View.VISIBLE);
            } else {
                mDebtorDataFields.setVisibility(View.GONE);
            }
        }
    };

    /**
     * Starts payment on button click.
     */
    private Button.OnClickListener mCreatePaymentButtonClickedListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Set the status and error messages gone and disable the button
            mStatusTextView.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.GONE);
            mCreatePaymentButton.setEnabled(false);

            postPayment();
        }
    };

    // Communication
    /**
     * Posts payment request.
     */
    private void postPayment() {
        PaymentsService paymentsService =
                RestApiClientInstance.getRestApiClientInstance(String.valueOf(Objects.requireNonNull(mBackendUrl.getEditText()).getText())).create(PaymentsService.class);

        PaymentSetupPOSTRequest paymentSetupPOSTRequest = buildPaymentSetupPOSTRequest();

        Call<PaymentSetupPOSTResponse> call = paymentsService.postPayments(paymentSetupPOSTRequest, generateIdempotencyKey(), EMPTY_STRING);
        call.enqueue(new Callback<PaymentSetupPOSTResponse>() {
            @Override
            public void onResponse(@NonNull Call<PaymentSetupPOSTResponse> call, @NonNull Response<PaymentSetupPOSTResponse> response) {
                Log.d(this.getClass().getName(), response.toString());

                if (response.code() >= 400) {
                    handleResponseError(response);
                }

                if (response.body() != null) {
                    onPaymentSetupPostResponseReceived(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PaymentSetupPOSTResponse> call, @NonNull Throwable t) {
                mErrorTextView.setText(t.getMessage());
                mErrorTextView.setVisibility(View.VISIBLE);
                mCreatePaymentButton.setEnabled(true);
            }
        });
    }

    /**
     * Requests authorization url after payment setup post response received.
     *
     * @param paymentSetupPOSTResponse
     */
    private void onPaymentSetupPostResponseReceived(PaymentSetupPOSTResponse paymentSetupPOSTResponse) {
        String paymentId = paymentSetupPOSTResponse.getPaymentSetupResponse().getPaymentId();

        AuthorizationService authorizationService =
                RestApiClientInstance.getRestApiClientInstance(String.valueOf(Objects.requireNonNull(mBackendUrl.getEditText()).getText())).create(AuthorizationService.class);

        Call<String> call = authorizationService.getAuthorizationUrl(paymentId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d(this.getClass().getName(), response.toString());

                if (response.code() >= 400) {
                    handleResponseError(response);
                }

                if (response.body() != null) {
                    onGetAuthorizationUrlResponseReceived(response.body(), paymentId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                mErrorTextView.setText(t.getMessage());
                mErrorTextView.setVisibility(View.VISIBLE);
                mCreatePaymentButton.setEnabled(true);
            }
        });
    }

    /**
     * Builds authorization request from authorization url and payment id.
     *
     * @param response The response of the getAuthorizationUrl request.
     * @param paymentId The payment id.
     */
    private void onGetAuthorizationUrlResponseReceived(String response, String paymentId) {
        Uri responseUri = Uri.parse(response);
        Uri.Builder uriBuilder = new Uri.Builder();
        Uri authEndpoint = uriBuilder
                .scheme(responseUri.getScheme())
                .authority(responseUri.getAuthority())
                .path(responseUri.getPath())
                .build();

        // Configuration
        AuthorizationServiceConfiguration serviceConfiguration =
                new AuthorizationServiceConfiguration(
                        authEndpoint,
                        Uri.parse("https://oidc-1.0.sandbox.mkb.hu/auth/realms/ftb-sandbox/protocol/openid-connect/token")); // token endpoint

        mAuthState = new AuthState(serviceConfiguration);

        // Build the request
        AuthorizationRequest.Builder authRequestBuilder = new AuthorizationRequest.Builder(
                serviceConfiguration,
                Objects.requireNonNull(responseUri.getQueryParameter("client_id")),
                Objects.requireNonNull(responseUri.getQueryParameter("response_type")),
                Uri.parse(responseUri.getQueryParameter("redirect_uri")));

        // Remove codeVerifier parameters
        authRequestBuilder.setCodeVerifier(null);

        Map<String, String> additionalParameters = new HashMap<String, String>();
        additionalParameters.put("request", responseUri.getQueryParameter("request"));
        AuthorizationRequest authorizationRequest = authRequestBuilder
                .setScope(responseUri.getQueryParameter("scope"))
                .setState(paymentId) // We have to set the paymentId as the state
                .setAdditionalParameters(additionalParameters)
                .build();

        doAuthorization(authorizationRequest);
    }

    /**
     * Starts authentication with chrome custom tab.
     *
     * @param authorizationRequest The authorization request.
     */
    private void doAuthorization(AuthorizationRequest authorizationRequest) {
        AppAuthConfiguration appAuthConfig = new AppAuthConfiguration.Builder()
                .setBrowserMatcher(new BrowserWhitelist(
                        VersionedBrowserMatcher.CHROME_CUSTOM_TAB))
                .build();

        mAuthorizationService = new net.openid.appauth.AuthorizationService(this, appAuthConfig);
        Intent authIntent = mAuthorizationService.getAuthorizationRequestIntent(authorizationRequest);
        startActivityForResult(authIntent, AUTH_REQUEST_CODE);
    }

    /**
     * Sends code ans state to backend after authentication redirect and receives payment submission data.
     *
     * @param code Code from authentication redirect.
     * @param state State from authentication redirect.
     */
    private void postAuthenticationCallback(String code, String state) {
        AuthorizationService authorizationService =
                RestApiClientInstance.getRestApiClientInstance(String.valueOf(Objects.requireNonNull(mBackendUrl.getEditText()).getText())).create(AuthorizationService.class);

        AuthCallbackPayload authCallbackPayload = new AuthCallbackPayload();
        authCallbackPayload.setCode(code);
        authCallbackPayload.setState(state);

        Call<PaymentSubmissionData> call = authorizationService.postAuthorazationCallback(authCallbackPayload);
        call.enqueue(new Callback<PaymentSubmissionData>() {
            @Override
            public void onResponse(@NonNull Call<PaymentSubmissionData> call, @NonNull Response<PaymentSubmissionData> response) {
                if (response.code() >= 400) {
                    handleResponseError(response);
                }

                if (response.body() != null
                        && response.body().getPaymentSubmission() != null
                        && response.body().getPaymentSubmission().getStatus() != null) {
                    mStatusTextView.setVisibility(View.VISIBLE);
                    mStatusTextView.setText(String.format(getString(R.string.mainActivity_statusText), response.body().getPaymentSubmission().getStatus()));
                    mCreatePaymentButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PaymentSubmissionData> call, @NonNull Throwable t) {
                mErrorTextView.setText(t.getMessage());
                mErrorTextView.setVisibility(View.VISIBLE);
                mCreatePaymentButton.setEnabled(true);
            }
        });
    }

    // Store, read and clear AuthState
    // NOTE: not really needed.
    /**
     * Persist the AuthState.
     * @param authState The AuthState.
     */
    private void persistAuthState(@NonNull AuthState authState) {
        getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString(AUTH_STATE, authState.jsonSerializeString())
                .apply();
    }

    /**
     * Clears AuthState from the shared preferences.
     */
    private void clearAuthState() {
        getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_STATE)
                .apply();
    }

    /**
     * Restores auth state from shared preferences.
     *
     * @return AuthsState The AuthState.
     */
    @Nullable
    private AuthState restoreAuthState() {
        String jsonString = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(AUTH_STATE, null);

        if (!TextUtils.isEmpty(jsonString)) {
            try {
                return AuthState.jsonDeserialize(jsonString);
            } catch (JSONException jsonException) {
                // should never happen
            }
        }

        return null;
    }

    // Helpers
     /**
     * Reads the input views and builds a PaymentSetupPOSTRequest.
     *
     * @return PaymentSetupPOSTRequest created from the input values.
     */
    private PaymentSetupPOSTRequest buildPaymentSetupPOSTRequest() {
        PaymentSetupPOSTRequest paymentSetupPOSTRequest = new PaymentSetupPOSTRequest();

        PaymentSetupInitiation paymentSetupInitiation = new PaymentSetupInitiation();
        paymentSetupInitiation.setCreditorAccount(new CreditorAccount());
        paymentSetupInitiation.getCreditorAccount().setSchemeName(Objects.requireNonNull(mCreditorAccountSchemeName.getEditText()).getText().toString());
        paymentSetupInitiation.getCreditorAccount().setIdentification(Objects.requireNonNull(mCreditorAccountIdentification.getEditText()).getText().toString());
        paymentSetupInitiation.getCreditorAccount().setAccountName(Objects.requireNonNull(mCreditorAccountName.getEditText()).getText().toString());
        paymentSetupInitiation.getCreditorAccount().setSecondaryIdentification(Objects.requireNonNull(mCreditorSecondaryIdentification.getEditText()).getText().toString());

        paymentSetupInitiation.setCreditorAgent(new CreditorAgent());
        paymentSetupInitiation.getCreditorAgent().setIdentification(Objects.requireNonNull(mCreditorAgentIdentification.getEditText()).getText().toString());
        paymentSetupInitiation.getCreditorAgent().setSchemeName(Objects.requireNonNull(mCreditorAgentSchemeName.getEditText()).getText().toString());

        paymentSetupInitiation.setRemittanceInformation(new RemittanceInformation());
        paymentSetupInitiation.getRemittanceInformation().setReference(Objects.requireNonNull(mRemittanceInformationReference.getEditText()).getText().toString());
        paymentSetupInitiation.getRemittanceInformation().setUnstructured(Objects.requireNonNull(mRemittanceInformationUnstructured.getEditText()).getText().toString());

        paymentSetupInitiation.setInstructedAmount(new PaymentSetupInitiationInstructedAmount());
        paymentSetupInitiation.getInstructedAmount().setAmount(Objects.requireNonNull(mInstructedAmount.getEditText()).getText().toString());
        paymentSetupInitiation.getInstructedAmount().setCurrency(Objects.requireNonNull(mInstructedAmountCurrency.getEditText()).getText().toString());

        paymentSetupInitiation.setInstructionIdentification(Objects.requireNonNull(mInstructionIdentification.getEditText()).getText().toString());
        paymentSetupInitiation.setEndToEndIdentification(Objects.requireNonNull(mEndToEndIdentification.getEditText()).getText().toString());

        if (mWithdebtorDataCheckbox.isChecked()) {
            DebtorAccount debtorAccount = new DebtorAccount();
            DebtorAgent debtorAgent = new DebtorAgent();

            debtorAccount.setAccountName(Objects.requireNonNull(mDebtorAccountName.getEditText()).getText().toString());
            debtorAccount.setIdentification(Objects.requireNonNull(mDebtorAccountIdentification.getEditText()).getText().toString());
            debtorAccount.setSchemeName(Objects.requireNonNull(mDebtorAccountSchemeName.getEditText()).getText().toString());
            debtorAccount.setSecondaryIdentification(Objects.requireNonNull(mDebtorAccountSecondaryIdentification.getEditText()).getText().toString());

            debtorAgent.setIdentification(Objects.requireNonNull(mDebtorAgentIdentification.getEditText()).getText().toString());
            debtorAgent.setSchemeName(Objects.requireNonNull(mDebtorAgentSchemeName.getEditText()).getText().toString());

            paymentSetupInitiation.setDebtorAccount(debtorAccount);
            paymentSetupInitiation.setDebtorAgent(debtorAgent);
        }

        paymentSetupPOSTRequest.setPaymentSetupInitiationData(new PaymentSetupInitiationData());
        paymentSetupPOSTRequest.getPaymentSetupInitiationData().setPaymentSetupInitiation(paymentSetupInitiation);
        paymentSetupPOSTRequest.setRisk(new Risk());

        return paymentSetupPOSTRequest;
    }

    private void initializeUiFields() {
        mBackendUrl = findViewById(R.id.mainActivity_backendUrlTextInputLayout);
        mWithdebtorDataCheckbox = findViewById(R.id.mainActivity_withDebtorDataCheckBox);
        mCreatePaymentButton = findViewById(R.id.mainActivity_createPaymentButton);
        mInstructedAmount = findViewById(R.id.mainActivity_instructedAmountTextInputLayout);
        mInstructedAmountCurrency = findViewById(R.id.mainActivity_instructedAmountCurrencyTextInputLayout);
        mCreditorAccountIdentification = findViewById(R.id.mainActivity_creditorAccountIdentification);
        mCreditorAccountName = findViewById(R.id.mainActivity_creditorAccountName);
        mCreditorAccountSchemeName = findViewById(R.id.mainActivity_creditorAccountSchemeName);
        mCreditorSecondaryIdentification  = findViewById(R.id.mainActivity_creditorSecondaryIdentification);
        mCreditorAgentSchemeName = findViewById(R.id.mainActivity_creditorAgentSchemeName);
        mCreditorAgentIdentification = findViewById(R.id.mainActivity_creditorAgentIdentification);
        mRemittanceInformationReference = findViewById(R.id.mainActivity_remittanceInformationReference);
        mRemittanceInformationUnstructured = findViewById(R.id.mainActivity_remittanceInformationUnstructured);
        mInstructionIdentification = findViewById(R.id.mainActivity_instructionIdentification);
        mEndToEndIdentification = findViewById(R.id.mainActivity_endToEndIdentification);
        mDebtorDataFields = findViewById(R.id.mainActivity_debtorData_fields);
        mDebtorAgentSchemeName = findViewById(R.id.mainActivity_debtorAgentSchemeName);
        mDebtorAgentIdentification = findViewById(R.id.mainActivity_debtorAgentIdentification);
        mDebtorAccountSchemeName = findViewById(R.id.mainActivity_debtorAccountSchemeName);
        mDebtorAccountIdentification = findViewById(R.id.mainActivity_debtorAccountIdentification);
        mDebtorAccountName = findViewById(R.id.mainActivity_debtorAccountName);
        mDebtorAccountSecondaryIdentification = findViewById(R.id.mainActivity_debtorAccountSecondaryIdentification);
        mStatusTextView = findViewById(R.id.mainActivity_StatusText);
        mErrorTextView = findViewById(R.id.mainActivity_ErrorText);

        mWithdebtorDataCheckbox.setOnCheckedChangeListener(mWithDebtorDataCheckedChanageListener);
        mCreatePaymentButton.setOnClickListener(mCreatePaymentButtonClickedListener);
    }

    /**
     * Fills the input viewsw with sample data.
     */
    @SuppressLint("SetTextI18n")
    private void fillSampleData() {
        Objects.requireNonNull(mBackendUrl.getEditText()).setText("http://payment-init-backend.ftb-local");

        Objects.requireNonNull(mInstructedAmount.getEditText()).setText("1680.00");
        Objects.requireNonNull(mInstructedAmountCurrency.getEditText()).setText("HUF");
        Objects.requireNonNull(mInstructionIdentification.getEditText()).setText("mobilVallet123");
        Objects.requireNonNull(mEndToEndIdentification.getEditText()).setText("29152852756654");
        Objects.requireNonNull(mCreditorAccountSchemeName.getEditText()).setText("IBAN");
        Objects.requireNonNull(mCreditorAccountIdentification.getEditText()).setText("HU35120103740010183300200004");
        Objects.requireNonNull(mCreditorAccountName.getEditText()).setText("Deichmann Cipőkereskedelmi Korlátolt Felelősségű Társaság");
        Objects.requireNonNull(mCreditorSecondaryIdentification.getEditText()).setText("0002");
        Objects.requireNonNull(mCreditorAgentIdentification.getEditText()).setText("UBRTHUHB");
        Objects.requireNonNull(mCreditorAgentSchemeName.getEditText()).setText("BICFI");
        Objects.requireNonNull(mDebtorAgentSchemeName.getEditText()).setText("BICFI");
        Objects.requireNonNull(mDebtorAgentIdentification.getEditText()).setText("KELRHUHBABC");
        Objects.requireNonNull(mDebtorAccountSchemeName.getEditText()).setText("IBAN");
        Objects.requireNonNull(mDebtorAccountIdentification.getEditText()).setText("HU29144000180331060700000000");
        Objects.requireNonNull(mDebtorAccountName.getEditText()).setText("Kiss Pista");
        Objects.requireNonNull(mDebtorAccountSecondaryIdentification.getEditText()).setText("0002");
        Objects.requireNonNull(mRemittanceInformationReference.getEditText()).setText("FRESCO-101");
        Objects.requireNonNull(mRemittanceInformationUnstructured.getEditText()).setText("Internal ops code 5120101");
    }

    /**
     * Generates a 15 char length random alphanumeric string.
     *
     * @return The idempotency key.
     */
    private String generateIdempotencyKey() {
        String data = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOP1234567890";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 15; i++) {
            stringBuilder.append(data.charAt(random.nextInt(data.length())));
        }

        return stringBuilder.toString();
    }

    private void handleResponseError(Response response) {
        if (response.errorBody() != null) {
            try {
                mErrorTextView.setText(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mErrorTextView.setText(response.message());
        }

        mErrorTextView.setVisibility(View.VISIBLE);
        mCreatePaymentButton.setEnabled(true);
    }
}
