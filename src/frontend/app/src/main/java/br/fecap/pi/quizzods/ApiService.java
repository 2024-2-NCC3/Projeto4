package br.fecap.pi.quizzods;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// interface para serviço de API
public interface ApiService {
    @POST("/criarUsuario")

    Call<UpRequest.SignUpResponse> signUp(@Body UpRequest signUpRequest);
}


