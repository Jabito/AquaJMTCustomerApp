package com.mapua.aquajmt.customerapp.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.mapua.aquajmt.customerapp.R;
import com.mapua.aquajmt.customerapp.adapters.OrdersAdapter;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersErrorItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersInfoItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersLabelItem;
import com.mapua.aquajmt.customerapp.adapters.items.OrdersMessageItem;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.OrderInfo;
import com.mapua.aquajmt.customerapp.api.retrofit.RetroFitApiImpl;
import com.mapua.aquajmt.customerapp.fragments.RateOrderFragment;
import com.mapua.aquajmt.customerapp.sqlite.LoginDbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, RateOrderFragment.RateOrderFragmentListener {

    @BindView(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lst_orders) ListView lstOrders;

    private CustomerInfo customerInfo;
    private RetroFitApiImpl retroFitApi;

    private ArrayList<OrdersAdapter.OrdersArrayAdapterItem> ordersArrayAdapterItems;
    private OrdersAdapter ordersAdapter;

    public OrdersActivity() {
        ordersArrayAdapterItems = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Orders");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        if (savedInstanceState == null) {
            ordersAdapter = new OrdersAdapter(this, ordersArrayAdapterItems);
        }

        registerForContextMenu(lstOrders);
        lstOrders.setAdapter(ordersAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        retroFitApi = new RetroFitApiImpl(Api.API_ENDPOINT);
        customerInfo = LoginDbHelper.getTop1(this);
        if (customerInfo == null)
            throw new AssertionError("This activity should have not been accessible.");
        else
            onRefresh();
    }

    @Override
    public void onRefresh() {
        ordersAdapter.clear();
        ordersAdapter.setNotifyOnChange(false);
        retrievePendingOrders();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.order_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.rate_order:
                OrdersInfoItem ordersInfoItem = (OrdersInfoItem) ordersAdapter.getItem(menuInfo.position);
                if (ordersInfoItem != null) {
                    OrderInfo orderInfo = ordersInfoItem.getOrderInfo();
                    RateOrderFragment
                            .newInstance(orderInfo.getOrderedFrom(), orderInfo.getOrderedBy())
                            .show(getSupportFragmentManager(), "rateOrderFragment");
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void retrievePendingOrders() {
        ordersArrayAdapterItems.add(new OrdersLabelItem("Pending Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "PENDING", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    ordersArrayAdapterItems.add(new OrdersMessageItem("There are no pending orders."));
                else
                    for (OrderInfo info : orderInfo)
                        ordersArrayAdapterItems.add(new OrdersInfoItem(info));
                retrieveActiveOrders();
            }

            @Override
            public void error() {
                ordersArrayAdapterItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of pending orders."));
                retrieveActiveOrders();
            }
        });
    }

    private void retrieveActiveOrders() {
        ordersArrayAdapterItems.add(new OrdersLabelItem("Active Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "ACTIVE", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    ordersArrayAdapterItems.add(new OrdersMessageItem("There are no active orders."));
                else
                    for (OrderInfo info : orderInfo)
                        ordersArrayAdapterItems.add(new OrdersInfoItem(info));
                retrieveCancelledOrders();
            }

            @Override
            public void error() {
                ordersArrayAdapterItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of active orders."));
                retrieveCancelledOrders();
            }
        });
    }

    private void retrieveCancelledOrders() {
        ordersArrayAdapterItems.add(new OrdersLabelItem("Cancelled Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "CANCELLED", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    ordersArrayAdapterItems.add(new OrdersMessageItem("There are no cancelled orders."));
                else
                    for (OrderInfo info : orderInfo)
                        ordersArrayAdapterItems.add(new OrdersInfoItem(info));
                retrieveCompletedOrders();
            }

            @Override
            public void error() {
                ordersArrayAdapterItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of cancelled orders."));
                retrieveCompletedOrders();
            }
        });
    }

    private void retrieveCompletedOrders() {
        ordersArrayAdapterItems.add(new OrdersLabelItem("Completed Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "COMPLETED", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    ordersArrayAdapterItems.add(new OrdersMessageItem("There are no completed orders."));
                else
                    for (OrderInfo info : orderInfo)
                        ordersArrayAdapterItems.add(new OrdersInfoItem(info));
                postCallback();
            }

            @Override
            public void error() {
                ordersArrayAdapterItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of completed orders."));
                postCallback();
            }
        });
    }

    private void postCallback() {
        swipeRefreshLayout.setRefreshing(false);
        ordersAdapter.setNotifyOnChange(true);
        ordersAdapter.notifyDataSetChanged();
    }
}
