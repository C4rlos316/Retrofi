package com.prueba.retrofi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView=findViewById(R.id.listView);



        objetoBody();
    }

    //obtener todos los usuarios
    private void retrofitResponse() {

        //se manda a llamar la url de donde se saca
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //se crea el api de retrofit
        Api api = retrofit.create(Api.class);

        Call<List<Hero>> call = api.getHeroes();


        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {

                List<Hero> heroes=response.body();


                String[] heroesName=new String[heroes.size()];

                for (int i=0;i<heroes.size();i++){

                    heroesName[i]=heroes.get(i).getName();

                }

                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        heroesName));

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //obtener usuario id por objeto INT
    private void objetoQuery(){

        //se manda a llamar la url de donde se saca
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //se crea el api de retrofit
        Api api = retrofit.create(Api.class);

        Call<Hero> call = api.getFindUser("1");

        call.enqueue(new Callback<Hero>() {
            @Override
            public void onResponse(Call<Hero> call, Response<Hero> response) {

                Log.e("User",response.body().getId()+response.body().getName());
            }

            @Override
            public void onFailure(Call<Hero> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //obtener un string no un objeto
    private void objetoField(){
        //se manda a llamar la url de donde se saca
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //se crea el api de retrofit
        Api api = retrofit.create(Api.class);

        Call<String> call=api.getFindUserPost("hackro");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e("User",response.body().toString());


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    //obtener un string no un objeto
    private void objetoHeader(){
        //se manda a llamar la url de donde se saca
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //se crea el api de retrofit
        Api api = retrofit.create(Api.class);


        String auth="Basic"+ Base64.encodeToString(String.format("%s,%s","tutoriales","hackro")
        .getBytes(),Base64.NO_WRAP);

        Call<String> call=api.auth(auth);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e("User",response.body().toString());


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    //obtener un json lo parsea y lo vuelve a mandar
    private void objetoBody(){
        //se manda a llamar la url de donde se saca
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //se crea el api de retrofit
        Api api = retrofit.create(Api.class);


        Call<Hero> call=api.body(new Hero(
                "1","carlos","Hernandez","c4rlos"));



        call.enqueue(new Callback<Hero>() {
            @Override
            public void onResponse(Call<Hero> call, Response<Hero> response) {

                Log.e("Body",response.body().toString());


            }

            @Override
            public void onFailure(Call<Hero> call, Throwable t) { }
        });


    }

    public static class Peticion extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);

            Call<List<Hero>> call = api.getHeroes();


            try {
                for (Hero hero:call.execute().body())

                    Log.e("Respuesta",hero.getName());


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
