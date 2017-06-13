package com.mapua.aquajmt.customerapp.api.retrofit;

import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.RateOrderForm;
import com.mapua.aquajmt.customerapp.api.models.LoginForm;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.api.models.RegisterForm;
import com.mapua.aquajmt.customerapp.api.models.UpdateCustomerForm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Bryan on 6/13/2017.
 */

interface ApiService {
    @POST(Api.LOGIN_ENDPOINT)
    Call<ResponseBody> login(@Body LoginForm loginForm);

    @POST(Api.REGISTER_ENDPOINT)
    Call<ResponseBody> register(@Body RegisterForm registerForm);

    @POST(Api.FORGOT_PASSWORD_ENDPOINT)
    Call<ResponseBody> forgotPassword(@Query("email") String email);

    @POST(Api.UPDATE_CUSTOMER_ENDPOINT)
    Call<ResponseBody> updateCustomer(@Body UpdateCustomerForm updateCustomerForm);

    @POST(Api.CREATE_ORDER_ENDPOINT)
    Call<ResponseBody> createOrder(@Body OrderForm orderForm);

    @POST(Api.RATE_ORDER_ENDPOINT)
    Call<ResponseBody> rateOrder(@Body RateOrderForm rateOrderForm);

    @GET(Api.FIND_SHOPS_ENDPOINT)
    Call<ResponseBody> findShops(@Query("x1") double lat1, @Query("y1") double lng1,
                                 @Query("x2") double lat2, @Query("y2") double lng2);

    @GET(Api.GET_SHOP_INFO_ENDPOINT)
    Call<ResponseBody> getShopInfo(@Query("id") String shopId);

    @GET(Api.GET_SHOP_SALES_INFO_ENDPOINT)
    Call<ResponseBody> getShopSalesInfo(@Query("id") String shopId);
}
