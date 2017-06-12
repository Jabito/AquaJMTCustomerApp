package com.mapua.aquajmt.customerapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends DialogFragment {

    private static final String ALKALINE_STR = "Alkaline";
    private static final String PURIFIED_STR = "Purified";
    private static final String DISTILLED_STR = "Distilled";
    private static final String MINERAL_STR = "Mineral";

    public interface OrderFragmentListener {
        void order(OrderForm orderForm);
    }

    @BindView(R.id.txt_ordering_from) TextView txtOrderingFrom;
    @BindView(R.id.txt_price) TextView txtPrice;
    @BindView(R.id.spnr_water_type) Spinner spnrWaterType;
    @BindView(R.id.chk_swap_containers) CheckBox chkSwapContainers;

    @BindView(R.id.container_round) View containerRound;
    @BindView(R.id.txt_container_round_price) TextView txtContainerRoundPrice;
    @BindView(R.id.txt_container_round_count) EditText txtContainerRoundCount;

    @BindView(R.id.container_slim) View containerSlim;
    @BindView(R.id.txt_container_slim_price) TextView txtContainerSlimPrice;
    @BindView(R.id.txt_container_slim_count) EditText txtContainerSlimCount;

    private OrderFragmentListener mListener;
    private ShopInfo shopInfo;
    private ShopSalesInfo shopSalesInfo;

    private double totalPrice = 0.0;
    private double containerPrice = 0.0;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment initialize(ShopInfo shopInfo, ShopSalesInfo shopSalesInfo) {
        OrderFragment orderFragment = new OrderFragment();
        orderFragment.shopInfo = shopInfo;
        orderFragment.shopSalesInfo = shopSalesInfo;
        return orderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

        if (shopSalesInfo == null || shopInfo == null)
            throw new IllegalStateException("The shopInfo or the shopSalesInfo object " +
                    "cannot be null.");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderFragmentListener) {
            mListener = (OrderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OrderFragmentListener");
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

        try {
            setShopInfoInView(shopInfo);
            setShopSalesInfoInView(shopSalesInfo);

            spnrWaterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (((String) spnrWaterType.getSelectedItem())) {
                        case ALKALINE_STR:
                            containerPrice = shopSalesInfo.getAlkalinePrice();
                            break;
                        case DISTILLED_STR:
                            containerPrice = shopSalesInfo.getDistilledPrice();
                            break;
                        case PURIFIED_STR:
                            containerPrice = shopSalesInfo.getPurifiedPrice();
                            break;
                        case MINERAL_STR:
                            containerPrice = shopSalesInfo.getMineralPrice();
                            break;
                        default:
                            containerPrice = 0.0;
                            Log.e("OrderFragment", "Selected unknown value: "
                                    + ((String) spnrWaterType.getSelectedItem()).toLowerCase());
                            break;
                    }

                    txtContainerRoundPrice.setText(String.format(
                            Locale.getDefault(), "PHP %.2f", containerPrice));
                    txtContainerSlimPrice.setText(String.format(
                            Locale.getDefault(), "PHP %.2f", containerPrice));

                    updateTotalPriceText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            updateTotalPriceText();
        } catch (NoOfferedContainersException e) {
            Toast.makeText(getActivity(), "No available container types for this shop. " +
                            "Contact this shop for more information.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            dismiss();
        } catch (NoOfferedWaterTypesException e) {
            Toast.makeText(getActivity(), "No available water types for this shop. " +
                            "Contact this shop for more information.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            dismiss();
        }
    }

    @OnClick(R.id.btn_order)
    public void order() {
        OrderForm orderForm = new OrderForm();
        orderForm.setStoreId(shopInfo.getId());
        orderForm.setWaterType(((String) spnrWaterType.getSelectedItem()).toUpperCase());
        orderForm.setRoundOrdered(getCountFromTextView(txtContainerRoundCount));
        orderForm.setSlimOrdered(getCountFromTextView(txtContainerSlimCount));

        mListener.order(orderForm);
        dismiss();
    }

    @OnClick(R.id.btn_container_slim_count_dec)
    public void decrementContainerSlimCount() {
        int count = getCountFromTextView(txtContainerSlimCount);
        if (count > 0) {
            txtContainerSlimCount.setText(String.format(Locale.getDefault(), "%d", --count));
        }
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_slim_count_inc)
    public void incrementContainerSlimCount() {
        int count = getCountFromTextView(txtContainerSlimCount);
        txtContainerSlimCount.setText(String.format(Locale.getDefault(), "%d", ++count));
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_round_count_dec)
    public void decrementContainerRoundCount() {
        int count = getCountFromTextView(txtContainerRoundCount);
        if (count > 0) {
            txtContainerRoundCount.setText(String.format(Locale.getDefault(), "%d", --count));
        }
        updateTotalPriceText();
    }

    @OnClick(R.id.btn_container_round_count_inc)
    public void incrementContainerRoundCount() {
        int count = getCountFromTextView(txtContainerRoundCount);
        txtContainerRoundCount.setText(String.format(Locale.getDefault(), "%d", ++count));
        updateTotalPriceText();
    }

    private void updateTotalPriceText() {
        totalPrice = getCountFromTextView(txtContainerRoundCount) * containerPrice;
        totalPrice += getCountFromTextView(txtContainerSlimCount) * containerPrice;

        txtPrice.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
    }

    private int getCountFromTextView(EditText editText) {
        int count;
        try {
            count = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException nfe) {
            count = 0;
        }
        return count;
    }

    private void setShopInfoInView(ShopInfo shopInfo) {
        txtOrderingFrom.setText(getString(R.string.order_from_text_format,
                shopInfo.getBusinessName()));
        chkSwapContainers.setEnabled(shopInfo.isAllowSwap());
    }

    private void setShopSalesInfoInView(ShopSalesInfo shopSalesInfo) throws NoOfferedContainersException, NoOfferedWaterTypesException {
        containerRound.setVisibility(
                shopSalesInfo.isRoundOffered() ? View.VISIBLE : View.GONE);
        containerSlim.setVisibility(
                shopSalesInfo.isSlimOffered() ? View.VISIBLE : View.GONE);

        if (!shopSalesInfo.isRoundOffered() && !shopSalesInfo.isSlimOffered()) {
            throw new NoOfferedContainersException();
        }

        ArrayList<String> waterTypes = new ArrayList<>();
        if (shopSalesInfo.isAlkalineAvailable())
            waterTypes.add(ALKALINE_STR);
        if (shopSalesInfo.isPurifiedAvailable())
            waterTypes.add(PURIFIED_STR);
        if (shopSalesInfo.isDistilledAvailable())
            waterTypes.add(DISTILLED_STR);
        if (shopSalesInfo.isMineralAvailable())
            waterTypes.add(MINERAL_STR);

        ArrayAdapter<String> waterTypesAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, waterTypes);
        waterTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrWaterType.setAdapter(waterTypesAdapter);

        if (waterTypes.size() >= 1)
            spnrWaterType.setSelection(0);
        else {
            throw new NoOfferedWaterTypesException();
        }
    }

    private class NoOfferedContainersException extends Throwable { }

    private class NoOfferedWaterTypesException extends Throwable { }
}
