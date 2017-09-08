package com.mapua.aquajmt.customerapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderConfirmationFragment extends DialogFragment {

    public interface OrderConfirmationFragmentListener {
        void goBackToOrderForm(ShopInfo shopInfo, ShopSalesInfo shopSalesInfo, OrderForm orderForm);

        LatLng getCurrentLocation();

        void orderSucceeded();

        void orderFailed();
    }

    private static final String SHOP_INFO_PARAM = "shop_info_param";
    private static final String SHOP_SALES_INFO_PARAM = "shop_sales_info_param";
    private static final String ORDER_FORM_PARAM = "order_form_param";

    @BindView(R.id.txt_ordering_from)
    TextView txtOrderingFrom;
    @BindView(R.id.txt_water_type)
    TextView txtWaterType;
    @BindView(R.id.txt_is_swapping)
    TextView txtIsSwapping;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_delivery_details)
    EditText txtDeliveryDetails;
    @BindView(R.id.txt_delivery_address)
    EditText txtDeliveryAddress;

    @BindView(R.id.container_round)
    View containerRound;
    @BindView(R.id.txt_container_round_price)
    TextView txtContainerRoundPrice;
    @BindView(R.id.txt_container_round_count)
    TextView txtContainerRoundCount;
    @BindView(R.id.txt_container_round_subtotal)
    TextView txtContainerRoundSubtotal;

    @BindView(R.id.container_slim)
    View containerSlim;
    @BindView(R.id.txt_container_slim_price)
    TextView txtContainerSlimPrice;
    @BindView(R.id.txt_container_slim_count)
    TextView txtContainerSlimCount;
    @BindView(R.id.txt_container_slim_subtotal)
    TextView txtContainerSlimSubtotal;

    private OrderConfirmationFragmentListener mListener;
    private ShopInfo shopInfo;
    private ShopSalesInfo shopSalesInfo;
    private OrderForm orderForm;

    public OrderConfirmationFragment() {
        // Required empty public constructor
    }

    public static OrderConfirmationFragment newInstance(ShopInfo shopInfo,
                                                        ShopSalesInfo shopSalesInfo, OrderForm orderForm) {
        OrderConfirmationFragment fragment = new OrderConfirmationFragment();
        Bundle args = new Bundle();
        args.putParcelable(SHOP_INFO_PARAM, shopInfo);
        args.putParcelable(SHOP_SALES_INFO_PARAM, shopSalesInfo);
        args.putParcelable(ORDER_FORM_PARAM, orderForm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopInfo = getArguments().getParcelable(SHOP_INFO_PARAM);
            shopSalesInfo = getArguments().getParcelable(SHOP_SALES_INFO_PARAM);
            orderForm = getArguments().getParcelable(ORDER_FORM_PARAM);

            if (orderForm == null || shopInfo == null || shopSalesInfo == null)
                throw new IllegalStateException("The objects shopInfo, shopSalesInfo, " +
                        "and orderForm object cannot be null.");
        } else
            throw new IllegalArgumentException("No arguments supplied to this fragment.");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                getActivity().onBackPressed();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderConfirmationFragmentListener) {
            mListener = (OrderConfirmationFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OrderConfirmationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        setViews();
    }

    @OnClick(R.id.btn_confirm_order)
    public void confirmOrder() {
        String deliveryAddress = txtDeliveryAddress.getText().toString();
        String deliveryDetails = txtDeliveryDetails.getText().toString();
        if (deliveryAddress.equals("") || deliveryDetails.equals(""))
            Toast.makeText(getContext(), "Complete all fields.", Toast.LENGTH_SHORT).show();
        else {
            orderForm.setDeliveryAddress(deliveryAddress);
            orderForm.setDeliveryDetails(deliveryDetails);
            orderForm.setDeliveryLocation(mListener.getCurrentLocation());
            if (orderForm.getDeliveryLocation() == null) {
                LatLng ll = new LatLng(14.562432d, 121.021473d);
                orderForm.setDeliveryLocation(ll);
            }

            RetroFitApiImpl retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);
            retroFitApi.createOrder(orderForm, new Api.OrderListener() {
                @Override
                public void success() {
                    OrderConfirmationFragment.this.dismiss();
                    mListener.orderSucceeded();
                }

                @Override
                public void error() {
                    mListener.orderFailed();
                }
            });
        }
    }

    public void goBackToOrderForm() {
        mListener.goBackToOrderForm(shopInfo, shopSalesInfo, orderForm);
        dismiss();
    }

    private void setViews() {
        txtOrderingFrom.setText(getString(R.string.order_from_text_format,
                shopInfo.getBusinessName()));

        txtIsSwapping.setText(orderForm.isSwapping() ? "Yes" : "No");

        containerRound.setVisibility(
                shopSalesInfo.isRoundOffered() ? View.VISIBLE : View.GONE);
        containerSlim.setVisibility(
                shopSalesInfo.isSlimOffered() ? View.VISIBLE : View.GONE);

        double containerPrice;
        if (orderForm.getWaterType().equalsIgnoreCase(OrderFragment.ALKALINE_STR)) {
            containerPrice = shopSalesInfo.getAlkalinePrice();

        } else if (orderForm.getWaterType().equalsIgnoreCase(OrderFragment.DISTILLED_STR)) {
            containerPrice = shopSalesInfo.getDistilledPrice();

        } else if (orderForm.getWaterType().equalsIgnoreCase(OrderFragment.PURIFIED_STR)) {
            containerPrice = shopSalesInfo.getPurifiedPrice();

        } else if (orderForm.getWaterType().equalsIgnoreCase(OrderFragment.MINERAL_STR)) {
            containerPrice = shopSalesInfo.getMineralPrice();

        } else {
            throw new AssertionError("Unknown water type. " + orderForm.getWaterType());
        }

        String price = String.format(Locale.getDefault(), "PHP %.2f", containerPrice);

        txtContainerRoundPrice.setText(price);
        txtContainerSlimPrice.setText(price);

        txtContainerRoundCount.setText(String.format(Locale.getDefault(),
                "%d", orderForm.getRoundOrdered()));
        txtContainerSlimCount.setText(String.format(Locale.getDefault(),
                "%d", orderForm.getSlimOrdered()));

        double roundSubtotal = containerPrice * orderForm.getRoundOrdered();
        double slimSubtotal = containerPrice * orderForm.getSlimOrdered();
        double total = roundSubtotal + slimSubtotal;

        txtPrice.setText(String.format(Locale.getDefault(), "PHP %.2f", total));
        txtContainerRoundSubtotal.setText(String.format(
                Locale.getDefault(), "PHP %.2f", roundSubtotal));
        txtContainerSlimSubtotal.setText(String.format(
                Locale.getDefault(), "PHP %.2f", slimSubtotal));

        txtWaterType.setText(orderForm.getWaterType());
    }
}
