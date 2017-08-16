package com.cityconnect.api;




//import com.raincan.app.dto.ServerDate;

import com.cityconnect.model.SignInDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

	/*
    An invocation of a Retrofit method that sends a request to a webserver and returns a response.
	Each call yields its own HTTP request and response pair. Use clone to make multiple calls with
	the same parameters to the same webserver; this may be used to implement polling or to retry a
	failed call.
	Calls may be executed synchronously with execute, or asynchronously with enqueue. In either case
	the call can be canceled at any time with cancel. A call that is busy writing its request or reading
	its response may receive a IOException; this is working as designed.
	*/

    @POST("/api/admin/signIn")
    Call<SignInDTO> signIn(@Body SignInDTO signInDTO);

}