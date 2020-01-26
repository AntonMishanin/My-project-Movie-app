package com.example.testingarchitecturecomponent.data.api;

import com.example.testingarchitecturecomponent.data.api.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "5a4918c1f55c934a15498c03cfed43ce";
    public static final String BASE_URL_FOR_IMAGE = "https://image.tmdb.org/t/p/w780";

    private static retrofit2.Retrofit instance;

    public static retrofit2.Retrofit getRetrofitInstance() {
        if (instance == null) {


            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    HttpUrl url = chain.request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();

                    Request request = chain.request()
                            .newBuilder()
                            .url(url)
                            .build();

                    Response response = chain.proceed(request);

                    return response;
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();


            instance = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
