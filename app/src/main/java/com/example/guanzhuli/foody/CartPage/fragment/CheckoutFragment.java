package com.example.guanzhuli.foody.CartPage.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guanzhuli.foody.CartPage.adapter.CheckoutAdapter;
import com.example.guanzhuli.foody.R;
import com.paypal.android.sdk.payments.*;
import org.json.JSONException;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 *
 * Method payOrder() : pass infor to Paypal
 * Method registerOrder: register order when get success msg
 */
public class CheckoutFragment extends Fragment {
    private static final String TAG = "iCartPayment";
    //private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AfNfJY2QLMIzxPpAt97YVg4GKJtMa0k8wQICuFcwIdR6bR73oexStWMQfH0nirg-WlFradZHcPnCleZg";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private RecyclerView mRecyclerView;
    private TextView mTextMobile, mTextAddress, mTextTotal, mTextEditAddress, mTextEditMobil;
    private Button mButtonCheckout, mButtonCancel;
    public CheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_checkout);
        mRecyclerView.setAdapter(new CheckoutAdapter(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // initial button
        mButtonCheckout = (Button) view.findViewById(R.id.checkout_pay);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "checkout", Toast.LENGTH_LONG).show();
                payOrder();
            }
        });
        mButtonCancel = (Button) view.findViewById(R.id.checkout_cancel);
        // initial text
        mTextMobile = (TextView) view.findViewById(R.id.checkout_mobile);
        mTextAddress = (TextView) view.findViewById(R.id.checkout_address);
        mTextTotal = (TextView) view.findViewById(R.id.checkout_total);
        mTextEditMobil = (TextView) view.findViewById(R.id.checkout_edit_mobile);
        mTextEditMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit Number", Toast.LENGTH_SHORT).show();
            }
        });
        mTextEditAddress = (TextView) view.findViewById(R.id.checkout_edit_addr);
        mTextEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Show map", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == getActivity().RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        registerOrder();
                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
                Toast.makeText(getContext(),"Cancel", Toast.LENGTH_LONG).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getContext(), PayPalService.class));
    }

    private void payOrder() {
                /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(getContext(), PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getStuffToBuy(String paymentIntent) {
        //--- include an item list, payment amount details
        PayPalItem[] items =
                {
                        new PayPalItem("sample item #1", 2, new BigDecimal("87.50"), "USD",
                                "sku-12345678"),
                        new PayPalItem("free sample item #2", 1, new BigDecimal("0.00"),
                                "USD", "sku-zero-price"),
                        new PayPalItem("sample item #3 with a longer name", 6, new BigDecimal("37.99"),
                                "USD", "sku-33333")
                };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("7.21");
        BigDecimal tax = new BigDecimal("4.67");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "sample item", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");

        return payment;
    }

    private void registerOrder() {
    }
}
