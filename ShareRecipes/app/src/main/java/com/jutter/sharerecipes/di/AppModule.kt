package com.jutter.sharerecipes.di

import com.jutter.sharerecipes.BuildConfig
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.comtrollers.ChangeBottomTabController
import com.jutter.sharerecipes.common.base.BottomSheetDialogController
import com.jutter.sharerecipes.comtrollers.IngradientsController
import com.jutter.sharerecipes.comtrollers.PickPhotoController
import com.jutter.sharerecipes.server.TokenInterceptor
import com.jutter.sharerecipes.server.Api
import com.jutter.sharerecipes.server.ApiService
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(androidContext()))
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
    }

    single {
        get<Retrofit.Builder>()
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }

    single {
        get<Retrofit>().create(Api::class.java)
    }

    single {
        Realm.init(androidContext())
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .name("mai_app.db")
            .build()
        Realm.getInstance(config)
    }

    single {
        ApiService(get())
    }

    single {
        BottomSheetDialogController()
    }

    single {
        BottomVisibilityController()
    }

    single {
        ChangeBottomTabController()
    }

    single {
        CiceroneHolder()
    }

    single {
        IngradientsController()
    }

    single {
        PickPhotoController()
    }
}