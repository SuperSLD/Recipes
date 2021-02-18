package com.jutter.sharerecipes.server

import com.jutter.sharerecipes.models.DataWrapper
import com.jutter.sharerecipes.models.human.CategoryHuman
import com.jutter.sharerecipes.models.server.*
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*


interface Api {

    @POST("user/login")
    fun login(@Body loginBody: LoginBody) : Single<DataWrapper<LoginResponse>>

    @POST("user/register")
    fun register(@Body loginBody: LoginBody) : Single<DataWrapper<LoginResponse>>

    @POST("user/logout")
    fun logout() : Single<DataWrapper<Any>>

    @GET("user/profile")
    fun profile() : Single<DataWrapper<UserResponse>>

    @POST("user/update")
    fun profileUpdate(@Body userUpdateBody: UserUpdateBody) : Single<DataWrapper<Any>>

    @GET("ingradients/search/{text}")
    fun searchIngradients(@Path("text") text: String): Single<DataWrapper<MutableList<CategoryResponse>>>

    @POST("recipes/create")
    fun createRecipes(@Body createRecipesBody: CreateRecipesBody): Single<DataWrapper<Any>>

    @GET("recipes/all")
    fun userRecipes(): Single<DataWrapper<MutableList<RecipesResponse>>>

    @GET("recipes/byuser/{id}")
    fun recipesByUser(@Path("id") id: Int): Single<DataWrapper<ListByUserResponse>>

    @GET("recipes/searchbyuser/{text}")
    fun searchRecipesByUser(@Path("text") text: String): Single<DataWrapper<MutableList<RecipesResponse>>>

    @POST("recipes/like")
    fun like(@Body likeBody: LikeBody): Single<DataWrapper<Any>>

    @POST("recipes/byingradients")
    fun searchRecipesByIngradients(@Body searchRecipesByIngradientsBody: SearchRecipesByIngradientsBody): Single<DataWrapper<MutableList<RecipesResponse>>>

    @GET("recipes/recommendation")
    fun recommendation(): Single<DataWrapper<RecommendationResponse>>

    @GET("recipes/search/{text}")
    fun searchRecipes(@Path("text") text: String): Single<DataWrapper<MutableList<RecipesResponse>>>

    @Multipart
    @POST("images/uploadprofile")
    fun uploadProfileImage(@Part image: MultipartBody.Part?): Single<DataWrapper<String>>

    @GET("recipes/onerecipes/{id}")
    fun oneRecipes(@Path("id") id: String): Single<DataWrapper<RecipesResponse>>
}