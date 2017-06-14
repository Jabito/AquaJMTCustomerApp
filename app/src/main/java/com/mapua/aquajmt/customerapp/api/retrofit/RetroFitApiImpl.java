package com.mapua.aquajmt.customerapp.api.retrofit;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;
import com.mapua.aquajmt.customerapp.api.Api;
import com.mapua.aquajmt.customerapp.api.models.OrderInfo;
import com.mapua.aquajmt.customerapp.api.models.OrderJson;
import com.mapua.aquajmt.customerapp.api.models.RateOrderForm;
import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.api.models.LoginForm;
import com.mapua.aquajmt.customerapp.api.models.OrderForm;
import com.mapua.aquajmt.customerapp.api.models.RegisterForm;
import com.mapua.aquajmt.customerapp.api.models.UpdateCustomerForm;
import com.mapua.aquajmt.customerapp.models.ShopInfo;
import com.mapua.aquajmt.customerapp.models.ShopSalesInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bryan on 6/13/2017.
 */
public class RetroFitApiImpl extends Api {

    private ApiService apiService;

    public RetroFitApiImpl(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void login(String username, String password, final LoginListener loginListener) {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);

        apiService.login(loginForm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONObject customerInfoJson = responseJson.getJSONObject("customerInfo");

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setId(customerInfoJson.getString("id"));
                        customerInfo.setUsername(customerInfoJson.getString("username"));
                        customerInfo.setPassword(customerInfoJson.getString("password"));
                        customerInfo.setEmail(customerInfoJson.getString("email"));
                        customerInfo.setFirstName(customerInfoJson.getString("firstName"));
                        customerInfo.setLastName(customerInfoJson.getString("lastName"));
                        customerInfo.setMiddleName(customerInfoJson.getString("middleName"));
                        customerInfo.setCellphoneNo(customerInfoJson.getString("cellphoneNo"));
                        customerInfo.setLandline(customerInfoJson.getString("landline"));
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("createdOn")));
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("updatedOn")));
                        loginListener.success(customerInfo);

                    } else if (response.code() == 403) {
                        loginListener.incorrectOrNotFound();
                    } else {
                        loginListener.error();
                    }
                } catch (JSONException | IOException | ParseException e) {
                    e.printStackTrace();
                    loginListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loginListener.error();
            }
        });
    }

    @Override
    public void register(RegisterForm registerForm, final RegisterListener registerListener) {
        apiService.register(registerForm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONObject customerInfoJson = responseJson.getJSONObject("customerInfo");

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setId(customerInfoJson.getString("id"));
                        customerInfo.setUsername(customerInfoJson.getString("username"));
                        customerInfo.setPassword(customerInfoJson.getString("password"));
                        customerInfo.setEmail(customerInfoJson.getString("email"));
                        customerInfo.setFirstName(customerInfoJson.getString("firstName"));
                        customerInfo.setLastName(customerInfoJson.getString("lastName"));
                        customerInfo.setMiddleName(customerInfoJson.getString("middleName"));
                        customerInfo.setCellphoneNo(customerInfoJson.getString("cellphoneNo"));
                        customerInfo.setLandline(customerInfoJson.getString("landline"));
                        customerInfo.setCreatedOn(customerInfoJson.get("createdOn") != null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(customerInfoJson.getString("createdOn")) : null);
                        customerInfo.setCreatedOn(customerInfoJson.get("updatedOn") != null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(customerInfoJson.getString("updatedOn")) : null);
                        registerListener.success(customerInfo);

                    } else if (response.code() == 409) {
                        registerListener.usernameOrEmailAlreadyTaken();
                    } else {
                        registerListener.error();
                    }
                } catch (JSONException | IOException | ParseException e) {
                    e.printStackTrace();
                    registerListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                registerListener.error();
            }
        });
    }

    @Override
    public void forgotPassword(String email, final ForgotPasswordListener forgotPasswordListener) {
        apiService.forgotPassword(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    forgotPasswordListener.success();
                } else if (response.code() == 404) {
                    forgotPasswordListener.notRegistered();
                } else {
                    forgotPasswordListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                forgotPasswordListener.error();
            }
        });
    }

    @Override
    public void updateCustomer(UpdateCustomerForm updateCustomerForm, final UpdateCustomerListener updateCustomerListener) {
        apiService.updateCustomer(updateCustomerForm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONObject customerInfoJson = responseJson.getJSONObject("customerInfo");

                        CustomerInfo customerInfo = new CustomerInfo();
                        customerInfo.setId(customerInfoJson.getString("id"));
                        customerInfo.setUsername(customerInfoJson.getString("username"));
                        customerInfo.setPassword(customerInfoJson.getString("password"));
                        customerInfo.setEmail(customerInfoJson.getString("email"));
                        customerInfo.setFirstName(customerInfoJson.getString("firstName"));
                        customerInfo.setLastName(customerInfoJson.getString("lastName"));
                        customerInfo.setMiddleName(customerInfoJson.getString("middleName"));
                        customerInfo.setCellphoneNo(customerInfoJson.getString("cellphoneNo"));
                        customerInfo.setLandline(customerInfoJson.getString("landline"));
                        customerInfo.setCreatedOn(customerInfoJson.get("createdOn") != null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(customerInfoJson.getString("createdOn")) : null);
                        customerInfo.setCreatedOn(customerInfoJson.get("updatedOn") != null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(customerInfoJson.getString("updatedOn")) : null);
                        updateCustomerListener.success(customerInfo);

                    } else {
                        updateCustomerListener.error();
                    }
                } catch (JSONException | IOException | ParseException e) {
                    e.printStackTrace();
                    updateCustomerListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                updateCustomerListener.error();
            }
        });
    }

    @Override
    public void createOrder(OrderForm orderForm, final OrderListener orderListener) {
        apiService.createOrder(new OrderJson(orderForm)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    orderListener.success();
                } else {
                    orderListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                orderListener.error();
            }
        });
    }

    @Override
    public void rateOrder(RateOrderForm rateOrderForm, final RateOrderListener rateOrderListener) {
        apiService.rateOrder(rateOrderForm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    rateOrderListener.success();
                } else {
                    rateOrderListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                rateOrderListener.error();
            }
        });
    }

    @Override
    public void findShopsByBounds(LatLng bottomLeft, LatLng upperRight, final FindShopsByBoundsListener findShopsByBoundsListener) {
        apiService.findShops(upperRight.longitude, upperRight.latitude,
                bottomLeft.longitude, bottomLeft.latitude).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200 && response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body().string());
                            JSONArray shopsJson = responseJson.getJSONArray("shops");

                            ArrayList<ShopInfo> shopInfos = new ArrayList<>();
                            for (int i = 0; i < shopsJson.length(); i++) {
                                JSONObject shopInfoJson = shopsJson.getJSONObject(i);

                                ShopInfo shopInfo = new ShopInfo();
                                shopInfo.setId(shopInfoJson.getString("id"));
                                shopInfo.setBusinessName(shopInfoJson.getString("businessName"));
                                shopInfo.setAddress(shopInfoJson.getString("address"));
                                shopInfo.setCellphoneNo(shopInfoJson.getString("cellphoneNo"));
                                shopInfo.setAlternateNo(shopInfoJson.getString("alternateNo"));
                                shopInfo.setTimeOpen(Api.SIMPLE_TIME_FORMAT.parse(
                                        shopInfoJson.getString("timeOpen")));
                                shopInfo.setTimeClose(Api.SIMPLE_TIME_FORMAT.parse(
                                        shopInfoJson.getString("timeClose")));
                                shopInfo.setAllowSwap(shopInfoJson.getBoolean("allowSwap"));
                                shopInfo.setAccountVerified(shopInfoJson.getBoolean("accountVerified"));
                                shopInfo.setDaysAvailable(shopInfoJson.getString("daysAvailable"));
                                shopInfo.setOpenOnHolidays(shopInfoJson.getBoolean("openOnHolidays"));
                                shopInfo.setCreatedOn(shopInfoJson.get("createdOn") != null ?
                                        Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("createdOn")) : null);
                                shopInfo.setUpdatedOn(shopInfoJson.get("updatedOn") != null && !shopInfoJson.getString("updatedOn").equals("null") ?
                                        Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("updatedOn")) : null);
                                shopInfo.setUpdatedBy(shopInfoJson.getString("updatedBy"));
                                shopInfo.setLocation(new LatLng(shopInfoJson.getDouble("latitude"),
                                        shopInfoJson.getDouble("longitude")));
                                shopInfo.setRating(shopInfoJson.getDouble("rating"));

                                shopInfos.add(shopInfo);
                            }

                            findShopsByBoundsListener.success(shopInfos);
                        } else {
                            findShopsByBoundsListener.error();
                        }
                    } catch (JSONException | ParseException | IOException e) {
                        e.printStackTrace();
                        findShopsByBoundsListener.error();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                findShopsByBoundsListener.error();
            }
        });
    }

    @Override
    public void getShopInfo(String shopId, final GetShopInfoListener getShopInfoListener) {
        apiService.getShopInfo(shopId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONObject shopInfoJson = responseJson.getJSONObject("shop");

                        ShopInfo shopInfo = new ShopInfo();
                        shopInfo.setId(shopInfoJson.getString("id"));
                        shopInfo.setBusinessName(shopInfoJson.getString("businessName"));
                        shopInfo.setAddress(shopInfoJson.getString("address"));
                        shopInfo.setCellphoneNo(shopInfoJson.getString("cellphoneNo"));
                        shopInfo.setAlternateNo(shopInfoJson.getString("alternateNo"));
                        shopInfo.setTimeOpen(Api.SIMPLE_TIME_FORMAT.parse(shopInfoJson.getString("timeOpen")));
                        shopInfo.setTimeClose(Api.SIMPLE_TIME_FORMAT.parse(shopInfoJson.getString("timeClose")));
                        shopInfo.setAllowSwap(shopInfoJson.getBoolean("allowSwap"));
                        shopInfo.setAccountVerified(shopInfoJson.getBoolean("accountVerified"));
                        shopInfo.setDaysAvailable(shopInfoJson.getString("daysAvailable"));
                        shopInfo.setOpenOnHolidays(shopInfoJson.getBoolean("openOnHolidays"));
                        shopInfo.setCreatedOn(shopInfoJson.get("createdOn") != null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("createdOn")) : null);
                        shopInfo.setUpdatedOn(shopInfoJson.get("updatedOn") != null && !shopInfoJson.getString("updatedOn").equals("null") ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("updatedOn")) : null);
                        shopInfo.setUpdatedBy(shopInfoJson.getString("updatedBy"));
                        shopInfo.setLocation(new LatLng(shopInfoJson.getDouble("latitude"),
                                shopInfoJson.getDouble("longitude")));
                        shopInfo.setRating(shopInfoJson.getDouble("rating"));

                        getShopInfoListener.success(shopInfo);
                    } else if (response.code() == 404) {
                        getShopInfoListener.notFound();
                    } else {
                        getShopInfoListener.error();
                    }
                } catch (JSONException | ParseException | IOException e) {
                    e.printStackTrace();
                    getShopInfoListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getShopInfoListener.error();
            }
        });
    }

    @Override
    public void getOrders(String customerId, String status, final GetOrdersListener getOrdersListener) {
        apiService.getOrdersByStatusAndCustomerId(customerId, status).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONArray ordersJson = responseJson.getJSONArray("orders");

                        ArrayList<OrderInfo> orderInfos = new ArrayList<>();
                        for (int i = 0; i < ordersJson.length(); i++) {
                            JSONObject orderInfoJson = ordersJson.getJSONObject(i);

                            OrderInfo orderInfo = new OrderInfo();
                            orderInfo.setId(orderInfoJson.getString("id"));
                            orderInfo.setOrderedBy(orderInfoJson.getString("orderedBy"));
                            orderInfo.setOrderedFrom(orderInfoJson.getString("orderedFrom"));
                            orderInfo.setShopName(orderInfoJson.getString("shopName"));
                            orderInfo.setCustomerName(orderInfoJson.getString("customerName") != null ?
                                    orderInfoJson.getString("customerName") : null);
                            orderInfo.setCustomerAddress(orderInfoJson.getString("customerAddress"));
                            orderInfo.setLongitude(orderInfoJson.get("longitude") != null
                                    && orderInfoJson.get("longitude").equals("null") ?
                                    orderInfoJson.getDouble("longitude") : 0.0);
                            orderInfo.setLatitude(orderInfoJson.get("latitude") != null
                                    && orderInfoJson.get("latitude").equals("null") ?
                                    orderInfoJson.getDouble("latitude") : 0.0);
                            orderInfo.setWaterType(orderInfoJson.getString("waterType"));
                            orderInfo.setRoundOrdered(orderInfoJson.getInt("roundOrdered"));
                            orderInfo.setSlimOrdered(orderInfoJson.getInt("slimOrdered"));
                            orderInfo.setCostPerItem(orderInfoJson.getDouble("costPerItem"));
                            orderInfo.setTotalCost(orderInfoJson.getDouble("totalCost"));
                            orderInfo.setMoreDetails(orderInfoJson.getString("moreDetails"));
                            orderInfo.setCreatedOn(orderInfoJson.get("createdOn") != null ?
                                    Api.SIMPLE_DATETIME_FORMAT.parse(orderInfoJson.getString("createdOn")) : null);
                            orderInfo.setUpdatedOn(orderInfoJson.get("updatedOn") != null && !orderInfoJson.getString("updatedOn").equals("null")  ?
                                    Api.SIMPLE_DATETIME_FORMAT.parse(orderInfoJson.getString("updatedOn")) : null);
                            orderInfo.setUpdatedBy(orderInfoJson.getString("updatedBy"));
                            orderInfo.setRatingGiven(orderInfoJson.optInt("ratingGiven"));
                            orderInfo.setStatus(orderInfoJson.getString("status"));
                            orderInfo.setComments(orderInfoJson.getString("comments"));

                            orderInfos.add(orderInfo);
                        }

                        getOrdersListener.success(orderInfos);
                    } else {
                        getOrdersListener.error();
                    }
                } catch (JSONException | ParseException | IOException e) {
                    e.printStackTrace();
                    getOrdersListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getOrdersListener.error();
            }
        });
    }

    @Override
    public void getShopSalesInfo(String shopId, final GetShopSalesInfoListener getShopSalesInfoListener) {
        apiService.getShopSalesInfo(shopId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        JSONObject shopSalesInfoJson = responseJson.getJSONObject("shop");

                        ShopSalesInfo shopSalesInfo = new ShopSalesInfo();
                        shopSalesInfo.setRoundOffered(shopSalesInfoJson.getBoolean("roundOffered"));
                        shopSalesInfo.setSlimOffered(shopSalesInfoJson.getBoolean("slimOffered"));
                        shopSalesInfo.setRoundStock(shopSalesInfoJson.getInt("roundStock"));
                        shopSalesInfo.setSlimStock(shopSalesInfoJson.getInt("slimStock"));
                        shopSalesInfo.setDistilledAvailable(shopSalesInfoJson.getBoolean("distilled"));
                        shopSalesInfo.setPurifiedAvailable(shopSalesInfoJson.getBoolean("purified"));
                        shopSalesInfo.setMineralAvailable(shopSalesInfoJson.getBoolean("mineral"));
                        shopSalesInfo.setAlkalineAvailable(shopSalesInfoJson.getBoolean("alkaline"));
                        shopSalesInfo.setDistilledPrice(shopSalesInfoJson.optDouble("distilledPrice", 0.0));
                        shopSalesInfo.setPurifiedPrice(shopSalesInfoJson.optDouble("purifiedPrice", 0.0));
                        shopSalesInfo.setMineralPrice(shopSalesInfoJson.optDouble("mineralPrice", 0.0));
                        shopSalesInfo.setAlkalinePrice(shopSalesInfoJson.optDouble("alkalinePrice", 0.0));

                        getShopSalesInfoListener.success(shopSalesInfo);
                    } else if (response.code() == 404) {
                        getShopSalesInfoListener.notFound();
                    } else {
                        getShopSalesInfoListener.error();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    getShopSalesInfoListener.error();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getShopSalesInfoListener.error();
            }
        });
    }
}
