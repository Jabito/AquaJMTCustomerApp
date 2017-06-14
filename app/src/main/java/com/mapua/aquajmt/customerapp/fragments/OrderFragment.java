package com.mapua.aquajmt.customerapp.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;
import com.mapua.aquajmt.customerapp.sqlite.LoginDbHelper;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends DialogFragment {

    public interface OrderFragmentListener {
        void confirmOrder(ShopInfo shopInfo, ShopSalesInfo shopSalesInfo, OrderForm orderForm);
    }

    private static final String SHOP_INFO_PARAM = "shop_info_param";
    private static final String SHOP_SALES_INFO_PARAM = "shop_sales_info_param";
    private static final String ORDER_FORM_PARAM = "order_form_param";
    public static final String ALKALINE_STR = "Alkaline";
    public static final String PURIFIED_STR = "Purified";
    public static final String DISTILLED_STR = "Distilled";
    public static final String MINERAL_STR = "Mineral";

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
    private OrderForm orderForm;

    private double totalPrice = 0.0;
    private double containerPrice = 0.0;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(ShopInfo shopInfo, ShopSalesInfo shopSalesInfo) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putParcelable(SHOP_INFO_PARAM, shopInfo);
        args.putParcelable(SHOP_SALES_INFO_PARAM, shopSalesInfo);
        orderFragment.setArguments(args);
        return orderFragment;
    }

    public static OrderFragment newInstance(ShopInfo shopInfo, ShopSalesInfo shopSalesInfo, OrderForm orderForm) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putParcelable(SHOP_INFO_PARAM, shopInfo);
        args.putParcelable(SHOP_SALES_INFO_PARAM, shopSalesInfo);
        args.putParcelable(ORDER_FORM_PARAM, orderForm);
        orderFragment.setArguments(args);
        return orderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            shopInfo = getArguments().getParcelable(SHOP_INFO_PARAM);
            shopSalesInfo = getArguments().getParcelable(SHOP_SALES_INFO_PARAM);
            orderForm = getArguments().getParcelable(ORDER_FORM_PARAM);

            if (shopSalesInfo == null || shopInfo == null)
                throw new IllegalStateException("The shopInfo or the shopSalesInfo object " +
                        "cannot be null.");
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

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
            setViews();

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
                            Locale.getDefault(), getString(R.string.php_price_format), containerPrice));
                    txtContainerSlimPrice.setText(String.format(
                            Locale.getDefault(), getString(R.string.php_price_format), containerPrice));

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
        int roundCount = getCountFromTextView(txtContainerRoundCount);
        int slimCount = getCountFromTextView(txtContainerSlimCount);

        if (roundCount == 0 && slimCount == 0) {
            Toast.makeText(getActivity(), "You aren't ordering anything.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        CustomerInfo customerInfo = LoginDbHelper.getTop1(getActivity());

        OrderForm orderForm = new OrderForm();
        orderForm.setCustomerId(customerInfo.getId());
        orderForm.setStoreId(shopInfo.getId());
        orderForm.setWaterType(((String) spnrWaterType.getSelectedItem()).toUpperCase());
        orderForm.setRoundOrdered(roundCount);
        orderForm.setSlimOrdered(slimCount);
        orderForm.setSwapping(chkSwapContainers.isChecked());

        mListener.confirmOrder(shopInfo, shopSalesInfo, orderForm);
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

        txtPrice.setText(String.format(Locale.getDefault(), getString(R.string.php_price_format), totalPrice));
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

    private void setViews() throws NoOfferedContainersException, NoOfferedWaterTypesException {
        txtOrderingFrom.setText(getString(R.string.order_from_text_format,
                shopInfo.getBusinessName()));
        chkSwapContainers.setEnabled(shopInfo.isAllowSwap());

        containerRound.setVisibility(
                shopSalesInfo.isRoundOffered() ? View.VISIBLE : View.GONE);
        containerSlim.setVisibility(
                shopSalesInfo.isSlimOffered() ? View.VISIBLE : View.GONE);

        if (!shopSalesInfo.isRoundOffered() && !shopSalesInfo.isSlimOffered())
            throw new NoOfferedContainersException();

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
        else
            throw new NoOfferedWaterTypesException();

        if (orderForm != null) {
            int index = waterTypes.indexOf(orderForm.getWaterType());
            spnrWaterType.setSelection(index);

            chkSwapContainers.setChecked(orderForm.isSwapping());

            txtContainerRoundCount.setText(String.format(
                    Locale.getDefault(), "%d", orderForm.getRoundOrdered()));
            txtContainerSlimCount.setText(String.format(
                    Locale.getDefault(), "%d", orderForm.getSlimOrdered()));
        }
    }

    private class NoOfferedContainersException extends Throwable { }

    private class NoOfferedWaterTypesException extends Throwable { }
}
