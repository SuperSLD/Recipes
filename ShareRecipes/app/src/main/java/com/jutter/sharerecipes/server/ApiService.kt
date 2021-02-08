package com.jutter.sharerecipes.server

import com.jutter.sharerecipes.models.server.*
import okhttp3.MultipartBody

class ApiService(private var api: Api) {

    fun login(loginBody: LoginBody) = api.login(loginBody)

    fun register(loginBody: LoginBody) = api.register(loginBody)

    fun logout() = api.logout()

    fun profile() = api.profile()

    fun profileUpdate(userUpdateBody: UserUpdateBody) = api.profileUpdate(userUpdateBody)

    fun searchIngradients(text: String) = api.searchIngradients(text)

    fun createRecipes(createRecipesBody: CreateRecipesBody) = api.createRecipes(createRecipesBody)

    fun userRecipes() = api.userRecipes()

    fun recipesByUser(id: Int) = api.recipesByUser(id)

    fun searchRecipesByUser(text: String) = api.searchRecipesByUser(text)

    fun like(likeBody: LikeBody) = api.like(likeBody)

    fun searchRecipesByIngradients(searchRecipesByIngradientsBody: SearchRecipesByIngradientsBody) = api.searchRecipesByIngradients(searchRecipesByIngradientsBody)

    fun recommendation() = api.recommendation()

    fun searchRecipes(text: String) = api.searchRecipes(text)

    fun uploadProfileImage(image: MultipartBody.Part) = api.uploadProfileImage(image)

    fun oneRecipes(id: String) = api.oneRecipes(id)
}