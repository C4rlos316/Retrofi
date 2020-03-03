package com.prueba.retrofi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL="https://androidtutorials.herokuapp.com/";


    @GET(Constants.URL)
    Call<List<Hero>> getHeroes();

    @POST(Constants.URL)
    Call<List<Hero>> getPostHeroes();

    //Query obtener usuario por id pero en objeto INT
    @GET(Constants.FIND_USER)
    Call<Hero> getFindUser(@Query("id")String idUser);


    //Post obtener usuario por nombre al mandar
    @FormUrlEncoded
    @POST(Constants.FIND_USER_POST)
    Call<String> getFindUserPost(@Field("name")String nombre);


    //Saber si se inicio sesion
    @POST(Constants.AUTH)
    Call<String> auth(@Header("authorization")String auth);

    //retornar datos sin necesidad de poner varios elementos
    @POST("returnObject")
    Call<Hero> body(@Body Hero hero);



}
