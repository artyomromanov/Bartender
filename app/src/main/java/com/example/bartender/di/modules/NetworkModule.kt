package com.example.bartender.di.modules

<<<<<<< HEAD
import com.example.bartender.BASE_URL
import com.example.bartender.repository.network.CocktailsClient
=======
import com.example.bartender.util.BASE_URL
import com.example.bartender.model.CocktailsClient
>>>>>>> develop
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideSearchClient() : CocktailsClient {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build()
            .create(CocktailsClient::class.java)

    }

}