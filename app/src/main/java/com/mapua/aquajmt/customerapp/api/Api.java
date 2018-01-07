package com.mapua.aquajmt.customerapp.api;

import com.google.android.gms.maps.model.LatLng;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.OrderInfo;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.api.models.RateOrderForm;
import com.mapua.aquajmt.customerapp.api.models.RegisterForm;
import com.mapua.aquajmt.customerapp.api.models.UpdateCustomerForm;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bryan on 6/13/2017.
 */

public abstract class Api {

//    public static final String API_ENDPOINT = "http://10.0.2.2:8081/";
//    public static final String API_ENDPOINT = "http://www.aquajmt.com:8081/";
    /**
     * azure
     * */
//    public static final String API_ENDPOINT = "http://52.230.82.236:8081";
    /**
     * google cloud
     * */
    public static final String API_ENDPOINT = "http://104.199.210.129:8081";
    public static final String LOGIN_ENDPOINT = "api/loginCustomer";
    public static final String REGISTER_ENDPOINT = "api/addCustomerLogin";
    public static final String FORGOT_PASSWORD_ENDPOINT = "api/forgotPasswordCustomer";
    public static final String UPDATE_CUSTOMER_ENDPOINT = "api/updateCustomerLogin";
    public static final String CREATE_ORDER_ENDPOINT = "api/addOrderInfo";
    public static final String RATE_ORDER_ENDPOINT = "api/rateOrder";
    public static final String FIND_SHOPS_ENDPOINT = "api/findShopsByBounds";
    public static final String GET_ORDERS_BY_STATUS_AND_CUSTOMER_ID = "api/getOrdersByStatusAndCustomerId";
    public static final String GET_SHOP_INFO_ENDPOINT = "api/shop/info";
    public static final String GET_SHOP_SALES_INFO_ENDPOINT = "api/shop/sales/information";

    public static final SimpleDateFormat SIMPLE_DATETIME_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat SIMPLE_TIME_FORMAT
            = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public abstract void login(String username, String password, LoginListener loginListener);
    public abstract void register(RegisterForm registerForm, RegisterListener registerListener);
    public abstract void forgotPassword(String email, ForgotPasswordListener forgotPasswordListener);
    public abstract void updateCustomer(UpdateCustomerForm updateCustomerForm, UpdateCustomerListener updateCustomerListener);
    public abstract void createOrder(OrderForm orderForm, OrderListener orderListener);
    public abstract void rateOrder(RateOrderForm rateOrderForm, RateOrderListener rateOrderListener);
    public abstract void findShopsByBounds(LatLng upperLeft, LatLng bottomRight, FindShopsByBoundsListener findShopsByBoundsListener);
    public abstract void getOrders(String customerId, String status, GetOrdersListener getOrdersListener);
    public abstract void getShopInfo(String shopId, GetShopInfoListener getShopInfoListener);
    public abstract void getShopSalesInfo(String shopId, GetShopSalesInfoListener getShopSalesInfoListener);

    public interface LoginListener {
        void success(CustomerInfo customerInfo); // 200 NOTE: api returns the customer info
        void incorrectOrNotFound(); // 403
        void error();
    }

    public interface RegisterListener {
        void success(CustomerInfo customerInfo); // 200 NOTE: api just returns the submitted submitted data
        void usernameOrEmailAlreadyTaken();
        void error();
    }

    public interface ForgotPasswordListener {
        void success(); // 200 NOTE: no important data in request
        void notRegistered(); // 404
        void error();
    }

    public interface UpdateCustomerListener {
        void success(CustomerInfo customerInfo); // 200 NOTE: api just returns the submitted submitted data
        void error();
    }

    public interface OrderListener extends CommonListener {
        // 200 NOTE: api just returns the submitted submitted data
    }

    public interface RateOrderListener extends CommonListener {
    }

    public interface GetOrdersListener {
        void success(List<OrderInfo> orderInfo);
        void error();
    }

    public interface FindShopsByBoundsListener {
        void success(List<ShopInfo> shopInfos);
        void error();
    }

    public interface GetShopInfoListener {
        void success(ShopInfo shopInfo);
        void notFound();
        void error();
    }

    public interface GetShopSalesInfoListener {
        void success(ShopSalesInfo shopSalesInfo);
        void notFound();
        void error();
    }

    public interface CommonListener {
        void success();
        void error();
    }
}
