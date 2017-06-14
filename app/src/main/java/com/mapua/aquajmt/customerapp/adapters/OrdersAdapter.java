package com.mapua.aquajmt.customerapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersErrorItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersInfoItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersLabelItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersMessageItem;
import com.mapua.aquajmt.customerapp.api.models.OrderInfo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bryan on 6/14/2017.
 */

public class OrdersAdapter extends ArrayAdapter<OrdersAdapter.OrdersArrayAdapterItem> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "MMMM dd, yyyy HH:mm", Locale.getDefault());

    private static final int INFO_ITEM_TYPE = 0;
    private static final int LABEL_ITEM_TYPE = 1;
    private static final int MESSAGE_ITEM_TYPE = 2;
    private static final int ERROR_ITEM_TYPE = 3;

    public OrdersAdapter(@NonNull Context context, @NonNull List<OrdersArrayAdapterItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public int getItemViewType(int position) {
        OrdersArrayAdapterItem ordersArrayAdapterItem = getItem(position);
        if (ordersArrayAdapterItem instanceof OrdersInfoItem) {
            return INFO_ITEM_TYPE;
        } else if (ordersArrayAdapterItem instanceof OrdersLabelItem) {
            return LABEL_ITEM_TYPE;
        } else if (ordersArrayAdapterItem instanceof OrdersMessageItem) {
            return MESSAGE_ITEM_TYPE;
        } else if (ordersArrayAdapterItem instanceof OrdersErrorItem) {
            return ERROR_ITEM_TYPE;
        } else {
            throw new AssertionError("Unidentifiable OrdersArrayAdapterItem. Cannot " +
                    "retrieve the view type.");
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrdersArrayAdapterItem item = getItem(position);
        if (item == null)
            throw new IllegalStateException("Item at position " + position + " is null.");
        int type = getItemViewType(position);

        if (convertView == null) {
            convertView = inflateItem(parent, type);
        }

        switch (type) {
            case INFO_ITEM_TYPE:
                OrderInfo orderInfo = ((OrdersInfoItem) item).getOrderInfo();

                TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
                txtPrice.setText(getContext().getString(R.string.php_price_format, orderInfo.getTotalCost()));

                TextView txtSummary = (TextView) convertView.findViewById(R.id.txt_summary);
                txtSummary.setText(getContext().getString(R.string.order_summary_format, orderInfo.getWaterType(),
                        getContext().getString(R.string.php_price_format, orderInfo.getCostPerItem()),
                        orderInfo.getShopName(), false ? "with" : "without",
                        DATE_FORMAT.format(orderInfo.getCreatedOn())));

                TextView txtContainerRoundCount = (TextView) convertView.findViewById(R.id.txt_container_round_count);
                txtContainerRoundCount.setText(String.valueOf(orderInfo.getRoundOrdered()));

                TextView txtContainerSlimCount = (TextView) convertView.findViewById(R.id.txt_container_slim_count);
                txtContainerSlimCount.setText(String.valueOf(orderInfo.getSlimOrdered()));

                TextView txtDeliveryDetails = (TextView) convertView.findViewById(R.id.txt_delivery_details);
                txtDeliveryDetails.setText(orderInfo.getMoreDetails());

                TextView txtDeliveryAddress = (TextView) convertView.findViewById(R.id.txt_delivery_address);
                txtDeliveryAddress.setText(orderInfo.getCustomerAddress());

                break;
            case LABEL_ITEM_TYPE: {
                OrdersLabelItem ordersLabelItem = ((OrdersLabelItem) item);
                TextView txtLabel = (TextView) convertView.findViewById(R.id.txt_label);
                txtLabel.setText(ordersLabelItem.getLabel());
                break;
            }
            case MESSAGE_ITEM_TYPE: {
                OrdersMessageItem ordersMessageItem = (OrdersMessageItem) item;
                TextView txtMessage = (TextView) convertView.findViewById(R.id.txt_message);
                txtMessage.setText(ordersMessageItem.getMessage());
                break;
            }
            case ERROR_ITEM_TYPE: {
                OrdersErrorItem ordersErrorItem = (OrdersErrorItem) item;
                TextView txtMessage = (TextView) convertView.findViewById(R.id.txt_message);
                txtMessage.setText(ordersErrorItem.getMessage());
                break;
            }
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return INFO_ITEM_TYPE == getItemViewType(position);
    }

    private View inflateItem(ViewGroup parent, int type) {
        switch (type) {
            case INFO_ITEM_TYPE:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_order_info, parent, false);
            case LABEL_ITEM_TYPE:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_order_label, parent, false);
            case MESSAGE_ITEM_TYPE:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_order_message, parent, false);
            case ERROR_ITEM_TYPE:
                return LayoutInflater.from(getContext()).inflate(R.layout.item_order_error, parent, false);
            default:
                throw new AssertionError("Unidentifiable OrdersArrayAdapterItem. Cannot " +
                        "produce a view for this implementing class.");
        }
    }

    public interface OrdersArrayAdapterItem { }

}
