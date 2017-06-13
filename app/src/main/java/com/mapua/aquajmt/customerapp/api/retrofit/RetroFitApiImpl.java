package com.mapua.aquajmt.customerapp.api.retrofit;

import com.google.android.gms.maps.model.LatLng;
import com.mapua.aquajmt.customerapp.api.Api;
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
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("createdOn")));
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("updatedOn")));
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
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("createdOn")));
                        customerInfo.setCreatedOn(Api.SIMPLE_DATETIME_FORMAT
                                .parse(customerInfoJson.getString("updatedOn")));
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
        apiService.createOrder(orderForm).enqueue(new Callback<ResponseBody>() {
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
    public void findShopsByBounds(LatLng upperLeft, LatLng bottomRight, final FindShopsByBoundsListener findShopsByBoundsListener) {
        apiService.findShops(upperLeft.latitude, upperLeft.longitude,
                bottomRight.latitude, bottomRight.longitude).enqueue(new Callback<ResponseBody>() {
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
                            shopInfo.setCreatedOn(shopInfoJson.getString("createdOn") == null ?
                                    Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("createdOn")) : null);
                            shopInfo.setUpdatedOn(shopInfoJson.getString("updatedOn") == null ?
                                    Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("updatedOn")) : null);
                            shopInfo.setUpdatedBy(shopInfoJson.getString("updatedBy"));
                            shopInfo.setLocation(new LatLng(shopInfoJson.getDouble("latitude"),
                                    shopInfoJson.getDouble("longitude")));
                            shopInfo.setRating(shopInfoJson.getDouble("rating"));
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
                        shopInfo.setTimeOpen(Api.SIMPLE_TIME_FORMAT.parse(
                                shopInfoJson.getString("timeOpen")));
                        shopInfo.setTimeClose(Api.SIMPLE_TIME_FORMAT.parse(
                                shopInfoJson.getString("timeClose")));
                        shopInfo.setAllowSwap(shopInfoJson.getBoolean("allowSwap"));
                        shopInfo.setAccountVerified(shopInfoJson.getBoolean("accountVerified"));
                        shopInfo.setDaysAvailable(shopInfoJson.getString("daysAvailable"));
                        shopInfo.setOpenOnHolidays(shopInfoJson.getBoolean("openOnHolidays"));
                        shopInfo.setCreatedOn(shopInfoJson.getString("createdOn") == null ?
                                Api.SIMPLE_DATETIME_FORMAT.parse(shopInfoJson.getString("createdOn")) : null);
                        shopInfo.setUpdatedOn(shopInfoJson.getString("updatedOn") == null ?
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
                        shopSalesInfo.setDistilledPrice(shopSalesInfoJson.getDouble("distilledPrice"));
                        shopSalesInfo.setPurifiedPrice(shopSalesInfoJson.getDouble("purifiedPrice"));
                        shopSalesInfo.setMineralPrice(shopSalesInfoJson.getDouble("mineralPrice"));
                        shopSalesInfo.setAlkalinePrice(shopSalesInfoJson.getDouble("alkalinePrice"));

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
