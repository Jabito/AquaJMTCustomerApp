package com.mapua.aquajmt.customerapp.api.volley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.RateOrderForm;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.api.models.RegisterForm;
import com.mapua.aquajmt.customerapp.api.models.UpdateCustomerForm;

/**
 * Created by Bryan on 6/13/2017.
 */

public class VolleyApiImpl extends Api {

    private RequestQueue requestQueue;
    private String endpoint;

    public VolleyApiImpl(Context context, String endpoint) {
        this.endpoint = endpoint;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void login(String username, String password, final LoginListener loginListener) {
        requestQueue.add(new LoginRequest(endpoint, username, password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginListener.error();
                    }
                }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> networkResponse = super.parseNetworkResponse(response);


                return networkResponse;
            }
        });
    }

    @Override
    public void register(RegisterForm registerForm, RegisterListener registerListener) {

    }

    @Override
    public void forgotPassword(String email, ForgotPasswordListener forgotPasswordListener) {

    }

    @Override
    public void updateCustomer(UpdateCustomerForm updateCustomerForm, UpdateCustomerListener updateCustomerListener) {

    }

    @Override
    public void createOrder(OrderForm orderForm, OrderListener orderListener) {

    }

    @Override
    public void rateOrder(RateOrderForm rateOrderForm, RateOrderListener rateOrderListener) {

    }

    @Override
    public void findShopsByBounds(LatLng upperLeft, LatLng bottomRight, FindShopsByBoundsListener findShopsByBoundsListener) {

    }

    @Override
    public void getOrders(String customerId, String status, GetOrdersListener getOrdersListener) {

    }

    @Override
    public void getShopInfo(String shopId, GetShopInfoListener getShopInfoListener) {

    }

    @Override
    public void getShopSalesInfo(String shopId, GetShopSalesInfoListener getShopSalesInfoListener) {

    }
}
