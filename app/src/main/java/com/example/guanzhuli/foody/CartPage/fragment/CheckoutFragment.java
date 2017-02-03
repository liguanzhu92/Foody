package com.example.guanzhuli.foody.CartPage.fragment;
// Lily: Base function for PayPal.
// Xiao: Implemented check out item list via in-cart items.
//       Added tax and shipping fee.


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanzhuli.foody.CartPage.MapsActivity;
import com.example.guanzhuli.foody.CartPage.adapter.CheckoutAdapter;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.DBManipulation;
import com.example.guanzhuli.foody.controller.SPManipulation;
import com.example.guanzhuli.foody.controller.ShoppingCartItem;
import com.example.guanzhuli.foody.model.Food;
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


    // PayPal Initialization
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


    // Fragment Component Initialization
    private RecyclerView mRecyclerView;
    private TextView mTextMobile, mTextTotal, mTextEditAddress, mTextEditMobil;
    public static TextView mTextAddress;
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
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        // initial text
        mTextMobile = (TextView) view.findViewById(R.id.checkout_mobile);
        mTextMobile.setText(SPManipulation.getInstance(getActivity()).getMobile());
        mTextAddress = (TextView) view.findViewById(R.id.checkout_address);
        mTextAddress.setText(SPManipulation.getInstance(getContext()).getAddress());
        mTextTotal = (TextView) view.findViewById(R.id.checkout_total);
        mTextEditMobil = (TextView) view.findViewById(R.id.checkout_edit_mobile);
        mTextEditMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                Toast.makeText(getContext(), "Edit Number", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_mobile,(ViewGroup) view.findViewById(R.id.dialog_mobile));
                new AlertDialog.Builder(getActivity()).setTitle("Please Input Contact Information").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputMobile = (EditText) dialog.findViewById(R.id.dialog_et_mobile);
                        if (inputMobile.getText().toString().isEmpty()){
                            return;
                        }
                        try{
                            long number = Long.valueOf(inputMobile.getText().toString());
                            SPManipulation.getInstance(getActivity()).setMobile(inputMobile.getText().toString());
                            mTextMobile.setText(inputMobile.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Please Input Correct Phone Number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });
        mTextEditAddress = (TextView) view.findViewById(R.id.checkout_edit_addr);

        mTextEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_location,(ViewGroup) view.findViewById(R.id.dialog_location));
                new AlertDialog.Builder(getActivity()).setTitle("Please Input Delivery Location").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputLocation = (EditText) dialog.findViewById(R.id.dialog_et_location);
                        if (inputLocation.getText().toString().isEmpty()){
                            return;
                        }
                        mTextAddress.setText(inputLocation.getText().toString());
                    }
                })
                        .setNeutralButton("Show Map", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent mapAct = new Intent(getActivity(), MapsActivity.class);
                                startActivity(mapAct);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        mTextTotal.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getPrice() * 1.06 + 1.99));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == getActivity().RESULT_OK) {
                ShoppingCartItem.getInstance(getContext()).placeOrder(mTextAddress.getText().toString(), mTextMobile.getText().toString());
                ShoppingCartItem.getInstance(getContext()).clear();
                DBManipulation.getInstance(getActivity()).deleteAll();
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
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
                HomePageActivity.cartNumber.setText("0");
                getActivity().finish();
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
        PayPalItem[] items = new PayPalItem[ShoppingCartItem.getInstance(getContext()).getFoodTypeSize()];
        for (int position = 0; position < ShoppingCartItem.getInstance(getContext()).getFoodTypeSize(); position++){
            int id = ShoppingCartItem.getInstance(getContext()).getFoodInCart().get(position);
            final Food curFood = ShoppingCartItem.getInstance(getContext()).getFoodById(id);
            final int curFoodNumber = ShoppingCartItem.getInstance(getContext()).getFoodNumber(curFood);
            Log.e("PRICE & NUMBER", "price: " + curFood.getPrice() + ", number: " + curFoodNumber);
            items[position] = new PayPalItem("Item No." + curFood.getId(),
                    curFoodNumber,
                    new BigDecimal(String.valueOf(curFood.getPrice())),
                    "USD",
                    curFood.getName()
            );
        }
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("1.99");
        BigDecimal tax = new BigDecimal("" + ShoppingCartItem.getInstance(getContext()).getPrice() * 0.06);
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "Foody Inc.", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");

        return payment;
    }

    private void registerOrder() {
    }
}
