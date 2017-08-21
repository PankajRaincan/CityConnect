package com.cityconnect.api;


import android.util.Log;

import com.cityconnect.model.AddService;
import com.cityconnect.model.SignInDTO;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




/*import com.raincan.app.model.block;*/


public class APIHelper {

    public interface OnRequestComplete<T> {
        boolean onSuccess(T object);

        void onFailure(String errorMessage);
    }

    private static APIHelper instance;
    private APIService apiService;

    public static synchronized APIHelper init() {
        if (null == instance) {
            instance = new APIHelper();
            instance.createRestAdapter();
        }
        return instance;
    }

    private void createRestAdapter() {
        // Define the interceptor, addbtn authentication headers
//		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//		loggingInterceptor.setLevel(Logger.isDebugEnabled() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(1, TimeUnit.MINUTES);
        okHttpClient.readTimeout(1, TimeUnit.MINUTES);
        okHttpClient.writeTimeout(1, TimeUnit.MINUTES);
        //okHttpClient.interceptors().addbtn(loggingInterceptor);
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("content-type", "application/json")
                        .build();

//                if (Logger.isDebugEnabled()) {
//                    app.getLogger().warn("Request URL >> " + request.url());
//                    app.getLogger().warn("Header Size >> " + request.headers().size());
//                    app.getLogger().warn("Request Headers >> ");
//                    for (String name : request.headers().names()) {
//                        app.getLogger().info("\n" + name + " --> " + request.headers().get(name));
//                    }
//                    app.getLogger().warn("Request Body >> ");
//                    if (null != request.body()) {
//                        Buffer bufferRequest = new Buffer();
//                        request.body().writeTo(bufferRequest);
//                        app.getLogger().warn(bufferRequest.readUtf8());
//                    }
//                }
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://139.59.15.121:3001/")
                .build();

        apiService = retrofit.create(APIService.class);
    }

    /****************************************************************
     * API Methods
     ****************************************************************/

    private String showGeneralizedError() {
        return "We apologize for the inconvenience. Please try again later";
    }

    private String showTimeoutError() {
        return "It seems, your internet connection is slow. You may try again after some time or switch to other connection.";
    }

    public void signIn(SignInDTO signInDTO, final OnRequestComplete onRequestComplete) {

        apiService.signIn(signInDTO).enqueue(new Callback<SignInDTO>() {
            @Override
            public void onResponse(Call<SignInDTO> call, Response<SignInDTO> response) {
                try {
                    if (response.code() == 200) {
                        onRequestComplete.onSuccess(true);
                    } else if (response.code() == 404) {
                        onRequestComplete.onSuccess(false);
                    } else {
                        onRequestComplete.onFailure(response.errorBody().toString());
                    }

                } catch (Exception e) {
                    onRequestComplete.onFailure(showGeneralizedError());
                }
            }

            @Override
            public void onFailure(Call<SignInDTO> call, Throwable t) {
                try {
                    if (t.getMessage() != null) {
                        onRequestComplete.onFailure(showTimeoutError());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onRequestComplete.onFailure(showTimeoutError());

                }

            }
        });
    }


    public void getAllServices(final OnRequestComplete onRequestComplete) {

        apiService.getAllServices().enqueue(new Callback<ArrayList<AddService>>() {
            @Override
            public void onResponse(Call<ArrayList<AddService>> call, Response<ArrayList<AddService>> response) {

                if (response.body() != null) {
                    try {
                        if (response.isSuccessful()) {
                            onRequestComplete.onSuccess(response.body());
                        } else {
                            onRequestComplete.onFailure(response.errorBody().toString());
                        }
                    } catch (Exception e) {
                        onRequestComplete.onFailure(showGeneralizedError());
                    }
                } else {
                    onRequestComplete.onFailure(showGeneralizedError());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AddService>> call, Throwable t) {

                t.printStackTrace();
                Log.v("Error--",""+t.getMessage());
                try {
                    if (t.getMessage() != null) {
                        onRequestComplete.onFailure(showTimeoutError());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onRequestComplete.onFailure(showTimeoutError());

                }

            }
        });
    }

    public void addService(File file, AddService user, final OnRequestComplete onRequestComplete) {
        RequestBody requestBody = null;
        String userString = new Gson().toJson(user);

        if (file != null) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image"), file))
                    .addFormDataPart("name", user.getName())
                    .addFormDataPart("email", user.getEmail())
                    .addFormDataPart("mobile", user.getMobile())
                    .addFormDataPart("address_line_one", user.getAddress1())
                    .addFormDataPart("address_line_two", user.getAddress2())
                    .addFormDataPart("dob", user.getDob())
                    .addFormDataPart("description", user.getDesc())
                    .addFormDataPart("lat", user.getLatitude())
                    .addFormDataPart("lng", user.getLongitude())
                    .build();
        } else {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", user.getName())
                    .addFormDataPart("email", user.getEmail())
                    .addFormDataPart("mobile", user.getMobile())
                    .addFormDataPart("address_line_one", user.getAddress1())
                    .addFormDataPart("address_line_two", user.getAddress2() != null ? user.getAddress2() : "")
                    .addFormDataPart("dob", user.getDob())
                    .addFormDataPart("description", user.getDesc())
                    .addFormDataPart("lat", user.getLatitude())
                    .addFormDataPart("lng", user.getLongitude())
                    .build();
        }

        apiService.addService(requestBody).enqueue(new Callback<AddService>() {
            @Override
            public void onResponse(Call<AddService> call, Response<AddService> response) {
                if (response.body() != null) {
                    try {
                        if (response.isSuccessful()) {
                            onRequestComplete.onSuccess(response.body());
                        } else {
                            onRequestComplete.onFailure(response.errorBody().toString());
                        }
                    } catch (Exception e) {
                        onRequestComplete.onFailure(showGeneralizedError());
                    }
                } else {
                    onRequestComplete.onFailure(showGeneralizedError());
                }
            }

            @Override
            public void onFailure(Call<AddService> call, Throwable t) {
                try {
                    if (t.getMessage() != null) {
//                        app.getLogger().error(t.getMessage());
                        onRequestComplete.onFailure(showTimeoutError());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onRequestComplete.onFailure(showTimeoutError());

                }
            }

        });
    }
}

