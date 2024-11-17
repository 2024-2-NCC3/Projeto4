package br.fecap.pi.quizzods;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// interface para serviço de API

public interface ApiService {
    @POST("/criarUsuario")


    Call<UpRequest.SignUpResponse> signUp(@Body UpRequest signUpRequest);

    @POST("/login")
   Call<UpRequest.LoginResponse> login(@Body UpRequest.LoginRequest loginRequest);



    @GET("/salt")
    Call<UpRequest.LoginRequest.SaltResponse> getSalt(@Query("username") String username);
}


