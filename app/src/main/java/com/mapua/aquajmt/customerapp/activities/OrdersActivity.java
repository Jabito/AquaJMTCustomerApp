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
import android.widget.Toast;

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

public class OrdersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lst_orders)
    ListView lstOrders;

    private CustomerInfo customerInfo;
    private RetroFitApiImpl retroFitApi;

    private OrdersAdapter ordersAdapter;
    private RateOrderFragment rateOrderFragment;

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
            ordersAdapter = new OrdersAdapter(this,
                    new ArrayList<OrdersAdapter.OrdersArrayAdapterItem>());
        }

        registerForContextMenu(lstOrders);
        lstOrders.setAdapter(ordersAdapter);
        lstOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Click pos " + i, Toast.LENGTH_SHORT).show();
                if (ordersAdapter.getItem(i) instanceof OrdersInfoItem) {
                    OrdersInfoItem oaai = (OrdersInfoItem) ordersAdapter.getItem(i);
                    rateOrderFragment = RateOrderFragment.newInstance(oaai.getOrderInfo().getOrderedFrom(), oaai.getOrderInfo().getId());

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_fragment, rateOrderFragment, "RATE_ORDER_FRAG_TAG")
                            .commit();
                }
            }
        });
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

        refresh();
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

    private ArrayList<OrdersAdapter.OrdersArrayAdapterItem> tempItems;

    private void refresh() {
        tempItems = new ArrayList<>();

        retrievePendingOrders();
    }

    private void retrievePendingOrders() {
        tempItems.add(new OrdersLabelItem("Pending Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "PENDING", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    tempItems.add(new OrdersMessageItem("There are no pending orders."));
                else
                    for (OrderInfo info : orderInfo)
                        tempItems.add(new OrdersInfoItem(info));
                retrieveActiveOrders();
            }

            @Override
            public void error() {
                tempItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of pending orders."));
                retrieveActiveOrders();
            }
        });
    }

    private void retrieveActiveOrders() {
        tempItems.add(new OrdersLabelItem("Active Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "ACTIVE", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    tempItems.add(new OrdersMessageItem("There are no active orders."));
                else
                    for (OrderInfo info : orderInfo)
                        tempItems.add(new OrdersInfoItem(info));
                retrieveCancelledOrders();
            }

            @Override
            public void error() {
                tempItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of active orders."));
                retrieveCancelledOrders();
            }
        });
    }

    private void retrieveCancelledOrders() {
        tempItems.add(new OrdersLabelItem("Cancelled Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "CANCELLED", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    tempItems.add(new OrdersMessageItem("There are no cancelled orders."));
                else
                    for (OrderInfo info : orderInfo)
                        tempItems.add(new OrdersInfoItem(info));
                retrieveCompletedOrders();
            }

            @Override
            public void error() {
                tempItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of cancelled orders."));
                retrieveCompletedOrders();
            }
        });
    }

    private void retrieveCompletedOrders() {
        tempItems.add(new OrdersLabelItem("Completed Orders"));
        retroFitApi.getOrders(customerInfo.getId(), "COMPLETED", new Api.GetOrdersListener() {
            @Override
            public void success(List<OrderInfo> orderInfo) {
                if (orderInfo.size() == 0)
                    tempItems.add(new OrdersMessageItem("There are no completed orders."));
                else
                    for (OrderInfo info : orderInfo)
                        tempItems.add(new OrdersInfoItem(info));
                postCallback();
            }

            @Override
            public void error() {
                tempItems.add(new OrdersErrorItem("There was an error in the retrieval " +
                        "of completed orders."));
                postCallback();
            }
        });
    }

    private void postCallback() {
        swipeRefreshLayout.setRefreshing(false);

        ordersAdapter.addAll(tempItems);
        ordersAdapter.notifyDataSetChanged();

        tempItems = null;
    }
}
