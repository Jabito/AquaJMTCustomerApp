package com.mapua.aquajmt.customerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;
import com.mapua.aquajmt.customerapp.utils.DateTimeUtils;
import com.mapua.aquajmt.customerapp.utils.ShopInfoUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopInfoFragment extends Fragment {

    public interface StoreInfoFragmentListener {
        void orderFromStore();
    }

    @BindView(R.id.txt_name) TextView txtName;
    @BindView(R.id.txt_address) TextView txtAddress;
    @BindView(R.id.txt_contact_no) TextView txtContactNo;
    @BindView(R.id.txt_rating) TextView txtRating;
    @BindView(R.id.txt_is_open_status) TextView txtIsOpenStatus;

    @BindView(R.id.img_first_star) ImageView imgFirstStar;
    @BindView(R.id.img_second_star) ImageView imgSecondStar;
    @BindView(R.id.img_third_star) ImageView imgThirdStar;
    @BindView(R.id.img_fourth_star) ImageView imgFourthStar;
    @BindView(R.id.img_fifth_star) ImageView imgFifthStar;

    @BindView(R.id.btn_order) Button btnOrder;
    @BindView(R.id.btn_more_info) Button btnMoreInfo;
    @BindView(R.id.more_store_info) View moreStoreInfo;

    @BindView(R.id.txt_schedule) TextView txtSchedule;
    @BindView(R.id.txt_holidays_schedule) TextView txtHolidaysSchedule;
    @BindView(R.id.txt_other_contact_no) TextView txtOtherContactNo;
    @BindView(R.id.badge_round_Container) View badgeRoundContainer;
    @BindView(R.id.badge_slim_Container) View badgeSlimContainer;
    @BindView(R.id.badge_alkaline_water_type) View badgeAlkalineWaterType;
    @BindView(R.id.badge_distilled_water_type) View badgeDistilledWaterType;
    @BindView(R.id.badge_purified_water_type) View badgePurifiedWaterType;
    @BindView(R.id.badge_mineral_water_type) View badgeMineralWaterType;

    private StoreInfoFragmentListener mListener;
    private boolean isMoreStoreInfoShown = false;

    public ShopInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StoreInfoFragmentListener) {
            mListener = (StoreInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement StoreInfoFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.btn_more_info)
    public void onClickBtnViewMore() {
        toggleMoreInfo();
    }

    @OnClick(R.id.btn_order)
    public void onClickBtnOrder() {
        mListener.orderFromStore();
    }

    public void setStoreInView(ShopInfo shopInfo) {
        if (shopInfo == null)
            throw new IllegalStateException("The shopInfo cannot be null.");

        txtName.setText(shopInfo.getBusinessName());
        txtAddress.setText(shopInfo.getAddress());
        txtContactNo.setText(shopInfo.getCellphoneNo());

        if (ShopInfoUtils.isShopOpen(shopInfo)) {
            txtIsOpenStatus.setText(getString(R.string.store_status_open));
            txtIsOpenStatus.setTextColor(ContextCompat.getColor(getActivity(),
                    android.R.color.holo_green_dark));
            btnOrder.setEnabled(true);
        } else {
            txtIsOpenStatus.setText(getString(R.string.store_status_closed));
            txtIsOpenStatus.setTextColor(ContextCompat.getColor(getActivity(),
                    android.R.color.holo_red_dark));
            btnOrder.setEnabled(false);
        }

        float rating = (float) shopInfo.getRating();
        txtRating.setText(String.format(Locale.getDefault(), "%.2f", rating));
        String stars = ShopInfoUtils.getStars(rating);
        setStar(imgFirstStar, stars.charAt(0));
        setStar(imgSecondStar, stars.charAt(1));
        setStar(imgThirdStar, stars.charAt(2));
        setStar(imgFourthStar, stars.charAt(3));
        setStar(imgFifthStar, stars.charAt(4));

        // views in more info container
        txtSchedule.setText(getString(R.string.store_open_time_format,
                DateTimeUtils.stringifyTime(shopInfo.getTimeOpen()),
                DateTimeUtils.stringifyTime(shopInfo.getTimeClose())));

        if (shopInfo.isOpenOnHolidays())
            txtHolidaysSchedule.setText(getString(R.string.store_open_holidays));
        else
            txtHolidaysSchedule.setText(getString(R.string.store_closed_holidays));

        if (!shopInfo.getAlternateNo().isEmpty())
            txtOtherContactNo.setText(shopInfo.getAlternateNo());
        else
            txtOtherContactNo.setText(getString(R.string.txt_no_other_contact_no));
    }

    public void setShopSalesInView(ShopSalesInfo shopSalesInfo) {
        if (shopSalesInfo == null)
            throw new IllegalStateException("The shopSalesInfo cannot be null.");

        badgeRoundContainer.setVisibility(
                shopSalesInfo.isRoundOffered() ? View.VISIBLE : View.GONE);
        badgeSlimContainer.setVisibility(
                shopSalesInfo.isSlimOffered() ? View.VISIBLE : View.GONE);

        badgeAlkalineWaterType.setVisibility(
                shopSalesInfo.isAlkalineAvailable() ? View.VISIBLE : View.GONE);
        badgeDistilledWaterType.setVisibility(
                shopSalesInfo.isDistilledAvailable() ? View.VISIBLE : View.GONE);
        badgeMineralWaterType.setVisibility(
                shopSalesInfo.isMineralAvailable() ? View.VISIBLE : View.GONE);
        badgePurifiedWaterType.setVisibility(
                shopSalesInfo.isPurifiedAvailable() ? View.VISIBLE : View.GONE);
    }

    public boolean getMoreStoreInfoVisibility() {
        return isMoreStoreInfoShown;
    }

    public void setMoreStoreInfoVisibility(boolean isVisible) {
        isMoreStoreInfoShown = !isVisible;
        toggleMoreInfo();
    }

    private void toggleMoreInfo() {
        if (isMoreStoreInfoShown) {
            moreStoreInfo.setVisibility(View.GONE);
            btnMoreInfo.setText(getString(R.string.lbl_more_info));
            isMoreStoreInfoShown = false;
        } else {
            moreStoreInfo.setVisibility(View.VISIBLE);
            btnMoreInfo.setText(getString(R.string.lbl_less_info));
            isMoreStoreInfoShown = true;
        }
    }

    private void setStar(ImageView imgStar, char starChar) {
        switch (starChar) {
            case '0': imgStar.setImageResource(R.drawable.ic_star_border_black); break;
            case '1': imgStar.setImageResource(R.drawable.ic_star_half_black); break;
            case '2': imgStar.setImageResource(R.drawable.ic_star_black); break;
            default:
                throw new IllegalArgumentException("The value of the `starChar` " +
                        "parameter is not acceptable.");
        }
    }
}
