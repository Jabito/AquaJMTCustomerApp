package com.mapua.aquajmt.customerapp.api.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.mapua.aquajmt.customerapp.api.Api;

/**
 * Created by Bryan on 6/13/2017.
 */

public class LoginRequest extends StringRequest {

    private String username;
    private String password;

    public LoginRequest(String endpoint, String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, endpoint + Api.LOGIN_ENDPOINT, listener, errorListener);
        this.username = username;
        this.password = password;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password).getBytes();
    }
}
