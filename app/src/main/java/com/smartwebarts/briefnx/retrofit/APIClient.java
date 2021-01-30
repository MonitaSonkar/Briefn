package com.smartwebarts.briefnx.retrofit;

import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    public static String BASE_URL = "http://briefn.in/briefn_copy/";
    public static Retrofit getClient() {
        if (retrofit == null) {
            ////////////////////////////////////////////////////

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(60, TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .writeTimeout(60, TimeUnit.MINUTES)
                    .build();


            ////////////////////////////////////////////////////
            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://hinduarmy.in/")
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
