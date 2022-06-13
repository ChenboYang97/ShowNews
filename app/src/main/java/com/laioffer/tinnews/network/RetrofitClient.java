package com.laioffer.tinnews.network;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitClient {

    private static final String API_KEY = "e59fa1077b1a4d568c40f63295dc8a5c";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
